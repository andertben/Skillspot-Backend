package de.skillspot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    @GetMapping("/categories")
    public ResponseEntity loadcategories(){
        return ResponseEntity.ok(null);
    }
}
