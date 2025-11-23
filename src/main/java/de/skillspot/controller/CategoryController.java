package de.skillspot.controller;

import de.skillspot.dto.CategoryDto;
import de.skillspot.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryDto> loadcategories(){
        return ResponseEntity.ok(categoryService.loadcategories());
           }
}
