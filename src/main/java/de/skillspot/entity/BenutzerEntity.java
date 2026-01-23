package de.skillspot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "benutzer", schema = "skillspot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenutzerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long benutzerId;
    String vorname;
    String nachname;
    String email;
    String passwordHash;
    @Column(unique = true)
    String auth0Sub;
    String rolle;
    String displayName;
    String address;
    BigDecimal locationLat;
    BigDecimal locationLon;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
