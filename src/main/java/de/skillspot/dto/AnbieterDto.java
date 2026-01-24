package de.skillspot.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
public class AnbieterDto {
    Long anbieterId;
    String firmenName;
    String beschreibung;
    BigDecimal locationLat;
    BigDecimal locationLon;
}
