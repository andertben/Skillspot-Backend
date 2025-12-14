package de.skillspot.controller;

import de.skillspot.dto.BewertungDto;
import de.skillspot.service.BewertungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BewertungController {

    private final BewertungService bewertungService;

    public BewertungController(BewertungService bewertungService) {
        this.bewertungService = bewertungService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<BewertungDto>> loadReviews() {
        return ResponseEntity.ok(bewertungService.loadReviews());
    }
}

