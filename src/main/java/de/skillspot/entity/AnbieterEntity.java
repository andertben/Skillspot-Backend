package de.skillspot.entity;

import jakarta.persistence.*;
import lombok.*;

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
    Double locationLat;
    Double locationLon;
}
