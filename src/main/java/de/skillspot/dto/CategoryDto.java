package de.skillspot.dto;

import lombok.*;

@Data
@Builder
public class CategoryDto {
    Long categoryId;
    String text;
}
