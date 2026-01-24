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
    Long dienstleistungId;
    
    Long anbieterId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anbieterId", insertable = false, updatable = false)
    private AnbieterEntity anbieter;
    
    Long kategorieId;
    String title;
    String beschreibung;
    BigDecimal preis;
}
