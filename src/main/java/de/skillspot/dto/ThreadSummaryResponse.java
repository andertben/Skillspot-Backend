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
public class ThreadSummaryResponse {
    private Long threadId;
    private Long dienstleistungId;
    private String dienstleistungTitle;
    private String anbieterName;
    private LocalDateTime lastMessageAt;
    private String lastMessageText;
    private Long unreadCount;
}
