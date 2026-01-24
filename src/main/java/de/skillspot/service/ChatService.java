package de.skillspot.service;

import de.skillspot.dto.ChatMessageDto;
import de.skillspot.dto.ThreadResponse;
import de.skillspot.dto.ThreadSummaryResponse;
import de.skillspot.dto.UnreadCountResponse;
import de.skillspot.entity.*;
import de.skillspot.repository.ChatMessageRepository;
import de.skillspot.repository.ChatThreadRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatService {

    private final ChatThreadRepository chatThreadRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final EntityManager entityManager;

    public ChatService(ChatThreadRepository chatThreadRepository, ChatMessageRepository chatMessageRepository, EntityManager entityManager) {
        this.chatThreadRepository = chatThreadRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public ChatThreadEntity getOrCreateThread(String userSub, Long dienstleistungId) {
        return chatThreadRepository.findByUserSubAndDienstleistungId(userSub, dienstleistungId)
                .orElseGet(() -> {
                    ChatThreadEntity newThread = ChatThreadEntity.builder()
                            .userSub(userSub)
                            .dienstleistungId(dienstleistungId)
                            .dienstleistung(entityManager.getReference(DienstleistungEntity.class, dienstleistungId))
                            .build();
                    chatThreadRepository.saveAndFlush(newThread);
                    
                    // Reload to get relationships (dienstleistung -> anbieter -> benutzer) populated
                    return chatThreadRepository.findByUserSubAndDienstleistungId(userSub, dienstleistungId)
                            .orElseThrow(() -> new RuntimeException("Failed to reload created thread"));
                });
    }

    @Transactional(readOnly = true)
    public List<ThreadSummaryResponse> getThreadSummaries(String userSub) {
        return chatThreadRepository.findAllByUserSubOrProviderSub(userSub).stream()
                .map(thread -> {
                    Optional<ChatMessageEntity> lastMessageOpt = chatMessageRepository.findLatestMessageInThread(thread.getThreadId());
                    Long unreadCount = chatMessageRepository.countUnreadMessages(thread.getThreadId(), userSub);

                    ThreadResponse tr = mapToThreadResponse(thread);

                    return ThreadSummaryResponse.builder()
                            .threadId(thread.getThreadId())
                            .dienstleistungId(thread.getDienstleistungId())
                            .dienstleistungTitle(tr.getDienstleistungTitle())
                            .anbieterName(tr.getAnbieterName())
                            .lastMessageAt(lastMessageOpt.map(ChatMessageEntity::getCreatedAt).orElse(thread.getCreatedAt()))
                            .lastMessageText(lastMessageOpt.map(ChatMessageEntity::getText).orElse(null))
                            .unreadCount(unreadCount)
                            .build();
                })
                .sorted(Comparator.comparing(ThreadSummaryResponse::getLastMessageAt).reversed())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UnreadCountResponse getTotalUnreadCount(String userSub) {
        return UnreadCountResponse.builder()
                .unreadCount(chatMessageRepository.countTotalUnreadMessages(userSub))
                .build();
    }

    @Transactional
    public void markThreadAsRead(Long threadId, String userSub) {
        ChatThreadEntity thread = chatThreadRepository.findById(threadId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thread not found"));

        validateParticipant(userSub, thread);
        chatMessageRepository.markMessagesAsRead(threadId, userSub);
    }

    @Transactional(readOnly = true)
    public List<ChatThreadEntity> listThreadsForUser(String userSub) {
        return chatThreadRepository.findAllByUserSubOrProviderSub(userSub);
    }

    @Transactional(readOnly = true)
    public ThreadResponse getThreadById(Long threadId, String userSub) {
        ChatThreadEntity thread = chatThreadRepository.findByThreadIdAndUserSub(threadId, userSub)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Thread not found or access denied"));

        return mapToThreadResponse(thread);
    }

    @Transactional
    public List<ChatMessageDto> getMessages(String userSub, Long threadId) {
        ChatThreadEntity thread = chatThreadRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("Thread not found"));

        validateParticipant(userSub, thread);

        // Mark messages as read when opening the thread
        chatMessageRepository.markMessagesAsRead(threadId, userSub);

        return chatMessageRepository.findAllByThreadIdOrderByCreatedAtAsc(threadId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatMessageDto sendMessage(String userSub, Long threadId, String text) {
        ChatThreadEntity thread = chatThreadRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("Thread not found"));

        validateParticipant(userSub, thread);

        ChatMessageEntity message = ChatMessageEntity.builder()
                .threadId(threadId)
                .senderSub(userSub)
                .text(text)
                .build();

        return mapToDto(chatMessageRepository.save(message));
    }

    private void validateParticipant(String userSub, ChatThreadEntity thread) {
        boolean isCustomer = Objects.equals(thread.getUserSub(), userSub);
        boolean isProvider = false;
        
        if (thread.getDienstleistung() != null && 
            thread.getDienstleistung().getAnbieter() != null && 
            thread.getDienstleistung().getAnbieter().getBenutzer() != null) {
            isProvider = Objects.equals(thread.getDienstleistung().getAnbieter().getBenutzer().getAuth0Sub(), userSub);
        }

        if (!isCustomer && !isProvider) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a participant of this thread");
        }
    }

    private ChatMessageDto mapToDto(ChatMessageEntity entity) {
        return ChatMessageDto.builder()
                .messageId(entity.getMessageId())
                .threadId(entity.getThreadId())
                .senderSub(entity.getSenderSub())
                .text(entity.getText())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ThreadResponse mapToThreadResponse(ChatThreadEntity thread) {
        String title = thread.getDienstleistung() != null ? thread.getDienstleistung().getTitle() : null;
        String providerName = null;
        if (thread.getDienstleistung() != null && thread.getDienstleistung().getAnbieter() != null) {
            AnbieterEntity anbieter = thread.getDienstleistung().getAnbieter();
            providerName = anbieter.getFirmenName();
            if (providerName == null || providerName.isBlank()) {
                BenutzerEntity benutzer = anbieter.getBenutzer();
                if (benutzer != null) {
                    providerName = benutzer.getDisplayName();
                    if (providerName == null || providerName.isBlank()) {
                        String vorname = benutzer.getVorname() != null ? benutzer.getVorname() : "";
                        String nachname = benutzer.getNachname() != null ? benutzer.getNachname() : "";
                        providerName = (vorname + " " + nachname).trim();
                    }
                }
            }
        }

        return ThreadResponse.builder()
                .threadId(thread.getThreadId())
                .dienstleistungId(thread.getDienstleistungId())
                .dienstleistungTitle(title)
                .anbieterName(providerName)
                .build();
    }
}
