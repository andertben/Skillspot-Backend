package de.skillspot.service;

import de.skillspot.dto.CategoryDto;
import de.skillspot.mapper.CategoryMapper;
import de.skillspot.store.CategoryStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryStore categoryStore;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryStore categoryStore, CategoryMapper categoryMapper) {
        this.categoryStore = categoryStore;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> loadcategories(){
        return categoryStore.loadcategories().stream().map(categoryMapper::toDto).toList();
    }
}
