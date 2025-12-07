package de.skillspot.mapper;

import de.skillspot.dto.CategoryDto;
import de.skillspot.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(CategoryEntity entity);

}
