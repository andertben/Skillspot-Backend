package de.skillspot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "anbieter", schema = "skillspot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnbieterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long anbieterId;
    
    Long benutzerId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benutzerId", insertable = false, updatable = false)
    private BenutzerEntity benutzer;
    
    String firmenName;
    String beschreibung;
    BigDecimal locationLat;
    BigDecimal locationLon;
}
