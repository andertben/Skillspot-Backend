package de.skillspot.controller;

import de.skillspot.dto.AnbieterDto;
import de.skillspot.service.AnbieterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnbieterController {

    private final AnbieterService anbieterService;

    public AnbieterController(AnbieterService anbieterService) {
        this.anbieterService = anbieterService;
    }

    @GetMapping("/providers")
    public ResponseEntity<List<AnbieterDto>> loadProviders() {
        return ResponseEntity.ok(anbieterService.loadProviders());
    }
}
