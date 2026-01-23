package de.skillspot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadResponse {
    private Long threadId;
    private Long dienstleistungId;
    private String dienstleistungTitle;
    private String anbieterName;
}
