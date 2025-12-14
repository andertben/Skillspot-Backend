package de.skillspot.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class BewertungDto {
    Long bewertungId;
    Long dienstleistungId;
    Long benutzerId;
    Long anbieterId;
    Long buchungId;
    Integer bewertung;
    String text;
    LocalDateTime erstellungsDatum;
}
