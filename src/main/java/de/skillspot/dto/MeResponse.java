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
public class MeResponse {
    private String sub;
    private String email;
    private String displayName;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
