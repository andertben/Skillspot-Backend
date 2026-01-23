package de.skillspot.controller;

import de.skillspot.dto.ChatMessageDto;
import de.skillspot.dto.CreateThreadRequest;
import de.skillspot.dto.SendMessageRequest;
import de.skillspot.dto.ThreadResponse;
import de.skillspot.entity.ChatThreadEntity;
import de.skillspot.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Operation(summary = "Create or get an existing chat thread for a service")
    @PostMapping("/threads")
    public ResponseEntity<ThreadResponse> getOrCreateThread(
            @RequestBody CreateThreadRequest request,
            JwtAuthenticationToken auth) {

        if (request.getDienstleistungId() == null) {
            return ResponseEntity.badRequest().build();
        }

        String userSub = auth.getToken().getClaimAsString("sub");
        ChatThreadEntity thread = chatService.getOrCreateThread(userSub, request.getDienstleistungId());

        return ResponseEntity.ok(chatService.mapToThreadResponse(thread));
    }

    @Operation(summary = "List all chat threads for the authenticated user")
    @GetMapping("/threads")
    public ResponseEntity<List<ThreadResponse>> listThreads(JwtAuthenticationToken auth) {
        String userSub = auth.getToken().getClaimAsString("sub");
        List<ThreadResponse> threads = chatService.listThreadsForUser(userSub)
                .stream()
                .map(chatService::mapToThreadResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(threads);
    }

    @Operation(summary = "Get header details for a specific chat thread")
    @GetMapping("/threads/{threadId}")
    public ResponseEntity<ThreadResponse> getThread(
            @PathVariable Long threadId,
            JwtAuthenticationToken auth) {
        String userSub = auth.getToken().getClaimAsString("sub");
        return ResponseEntity.ok(chatService.getThreadById(threadId, userSub));
    }

    @Operation(summary = "Get all messages for a specific chat thread")
    @GetMapping("/threads/{threadId}/messages")
    public ResponseEntity<List<ChatMessageDto>> getMessages(
            @PathVariable Long threadId,
            JwtAuthenticationToken auth) {
        String userSub = auth.getToken().getClaimAsString("sub");
        return ResponseEntity.ok(chatService.getMessages(userSub, threadId));
    }

    @Operation(summary = "Send a new message in a chat thread")
    @PostMapping("/threads/{threadId}/messages")
    public ResponseEntity<ChatMessageDto> sendMessage(
            @PathVariable Long threadId,
            @RequestBody SendMessageRequest request,
            JwtAuthenticationToken auth) {
        String userSub = auth.getToken().getClaimAsString("sub");
        return ResponseEntity.ok(chatService.sendMessage(userSub, threadId, request.getText()));
    }
}
