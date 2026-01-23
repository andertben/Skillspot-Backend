package de.skillspot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long messageId;
    private Long threadId;
    private String senderSub;
    private String text;
    private LocalDateTime createdAt;
}
