package de.skillspot.service;

import de.skillspot.dto.ChatMessageDto;
import de.skillspot.dto.ThreadResponse;
import de.skillspot.entity.AnbieterEntity;
import de.skillspot.entity.BenutzerEntity;
import de.skillspot.entity.ChatMessageEntity;
import de.skillspot.entity.ChatThreadEntity;
import de.skillspot.repository.ChatMessageRepository;
import de.skillspot.repository.ChatThreadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatService {

    private final ChatThreadRepository chatThreadRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatThreadRepository chatThreadRepository, ChatMessageRepository chatMessageRepository) {
        this.chatThreadRepository = chatThreadRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Transactional
    public ChatThreadEntity getOrCreateThread(String userSub, Long dienstleistungId) {
        return chatThreadRepository.findByUserSubAndDienstleistungId(userSub, dienstleistungId)
                .orElseGet(() -> {
                    log.info("Creating new chat thread for userSub: {} and dienstleistungId: {}", userSub, dienstleistungId);
                    ChatThreadEntity newThread = ChatThreadEntity.builder()
                            .userSub(userSub)
                            .dienstleistungId(dienstleistungId)
                            .build();
                    chatThreadRepository.saveAndFlush(newThread);
                    // Reload to get relationships (dienstleistung -> anbieter -> benutzer) populated
                    return chatThreadRepository.findByUserSubAndDienstleistungId(userSub, dienstleistungId)
                            .orElseThrow(() -> new RuntimeException("Failed to reload created thread"));
                });
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

    public List<ChatMessageDto> getMessages(String userSub, Long threadId) {
        ChatThreadEntity thread = chatThreadRepository.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("Thread not found"));

        validateParticipant(userSub, thread);

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
