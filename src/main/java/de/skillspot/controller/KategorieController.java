package de.skillspot.controller;

import de.skillspot.dto.KategorieDto;
import de.skillspot.service.KategorieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KategorieController {
    private final KategorieService kategorieService;

    public KategorieController(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<KategorieDto>> loadcategories() {
        return ResponseEntity.ok(kategorieService.loadcategories());
    }
}
