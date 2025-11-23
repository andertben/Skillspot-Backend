package de.skillspot.service;

import de.skillspot.dto.CategoryDto;
import de.skillspot.mapper.CategoryMapper;
import de.skillspot.store.CategoryStore;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryStore categoryStore;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryStore categoryStore, CategoryMapper categoryMapper) {
        this.categoryStore = categoryStore;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDto loadcategories(){
        return categoryMapper.toDto(categoryStore.loadcategories());

    }
}
