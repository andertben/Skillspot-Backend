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
    @Column(name = "anbieter_id")
    Long anbieterId;
    
    @Column(name = "benutzer_id")
    Long benutzerId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benutzer_id", insertable = false, updatable = false)
    private BenutzerEntity benutzer;
    
    @Column(name = "firmen_name")
    String firmenName;
    String beschreibung;
    @Column(name = "location_lat")
    BigDecimal locationLat;
    @Column(name = "location_lon")
    BigDecimal locationLon;
}
