package de.skillspot.dto;

import lombok.*;

@Data
@Builder
public class DienstleistungDto {
    Long dienstleistungId;
    Long anbieterId;
    Long kategorieId;
    String title;
    String beschreibung;
    Double preis;
}
