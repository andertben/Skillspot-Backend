package de.skillspot.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenutzerEntity {
    Long benutzerId;
    String vorname;
    String nachname;
    String email;
    String passwordHash;
}
