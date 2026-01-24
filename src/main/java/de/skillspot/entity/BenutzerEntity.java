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
    @Column(name = "benutzer_id")
    Long benutzerId;
    String vorname;
    String nachname;
    String email;
    @Column(name = "password_hash")
    String passwordHash;
    @Column(name = "auth0_sub", unique = true)
    String auth0Sub;
    String rolle;
    @Column(name = "display_name")
    String displayName;
    String address;
    @Column(name = "location_lat")
    BigDecimal locationLat;
    @Column(name = "location_lon")
    BigDecimal locationLon;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
