package de.skillspot.controller;

import de.skillspot.dto.KategorieDto;
import de.skillspot.dto.KategorieTreeDto;
import de.skillspot.service.KategorieService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kategorien")
public class KategorieController {
    private final KategorieService kategorieService;

    public KategorieController(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseEntity<List<KategorieDto>> loadcategories() {
        return ResponseEntity.ok(kategorieService.loadcategories());
    }

    @Operation(summary = "Get category tree (grouped by Oberkategorien)")
    @GetMapping("/tree")
    public ResponseEntity<List<KategorieTreeDto>> getCategoryTree() {
        return ResponseEntity.ok(kategorieService.getCategoryTree());
    }
}
