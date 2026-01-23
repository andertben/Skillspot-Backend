package de.skillspot.controller;

import de.skillspot.dto.BewertungDto;
import de.skillspot.service.BewertungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BewertungController {

    private final BewertungService bewertungService;

    public BewertungController(BewertungService bewertungService) {
        this.bewertungService = bewertungService;
    }

    @GetMapping({"/api/reviews", "/bewertungen", "/reviews"})
    public ResponseEntity<List<BewertungDto>> loadReviews() {
        return ResponseEntity.ok(bewertungService.loadReviews());
    }

    @GetMapping({"/api/reviews/service/{serviceId}", "/bewertungen/service/{serviceId}"})
    public ResponseEntity<List<BewertungDto>> findByServiceId(@PathVariable Long serviceId) {
        return ResponseEntity.ok(bewertungService.findByServiceId(serviceId));
    }

    @GetMapping({"/api/reviews/provider/{providerId}", "/bewertungen/provider/{providerId}"})
    public ResponseEntity<List<BewertungDto>> findByProviderId(@PathVariable Long providerId) {
        return ResponseEntity.ok(bewertungService.findByProviderId(providerId));
    }

    @GetMapping({"/api/reviews/average/{serviceId}", "/bewertungen/average/{serviceId}"})
    public ResponseEntity<Double> getAverageRating(@PathVariable Long serviceId) {
        return ResponseEntity.ok(bewertungService.getAverageRatingByServiceId(serviceId));
    }
}

