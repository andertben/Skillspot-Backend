package de.skillspot.mapper;

import de.skillspot.dto.CategoryDto;
import de.skillspot.entity.CategoryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-07T13:03:46+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.categoryId( entity.getCategoryId() );
        categoryDto.text( entity.getText() );

        return categoryDto.build();
    }
}
