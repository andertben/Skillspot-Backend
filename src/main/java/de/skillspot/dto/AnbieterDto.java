package de.skillspot.dto;

import lombok.*;

@Data
@Builder
public class AnbieterDto {
    Long anbieterId;
    String firmenName;
    String beschreibung;
    Double locationLat;
    Double locationLon;
}
