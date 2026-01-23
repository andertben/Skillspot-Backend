package de.skillspot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KategorieTreeDto {
    private Long id;
    private String name;
    private List<KategorieChildDto> children;
}
