package de.skillspot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "dienstleistung", schema = "skillspot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DienstleistungEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dienstleistung_id")
    Long dienstleistungId;
    
    @Column(name = "anbieter_id")
    Long anbieterId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anbieter_id", insertable = false, updatable = false)
    private AnbieterEntity anbieter;
    
    @Column(name = "kategorie_id")
    Long kategorieId;
    String title;
    String beschreibung;
    BigDecimal preis;
}
