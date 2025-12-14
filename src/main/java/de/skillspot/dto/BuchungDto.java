package de.skillspot.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class BuchungDto {
    Long buchungId;
    Long dienstleistungId;
    Long benutzerId;
    String text;
    LocalDateTime anfrageDatum;
    String status;
    Double preis;
}
