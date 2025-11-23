package de.skillspot.mapper;

import de.skillspot.dto.CategoryDto;
import de.skillspot.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(CategoryEntity entity);
}
