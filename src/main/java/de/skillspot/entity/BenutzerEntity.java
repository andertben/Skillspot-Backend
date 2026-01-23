package de.skillspot.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    String auth0Sub;
    String rolle;
    String displayName;
    String address;
    BigDecimal locationLat;
    BigDecimal locationLon;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
