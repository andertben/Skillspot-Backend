package de.skillspot.dto;

import lombok.*;

@Data
@Builder
public class BenutzerDto {
    Long benutzerId;
    String vorname;
    String nachname;
    String email;
}
