package de.skillspot.controller;

import de.skillspot.dto.AnbieterDto;
import de.skillspot.service.AnbieterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/anbieter")
public class AnbieterController {

    private final AnbieterService anbieterService;

    public AnbieterController(AnbieterService anbieterService) {
        this.anbieterService = anbieterService;
    }

    @GetMapping
    public ResponseEntity<List<AnbieterDto>> loadProviders() {
        return ResponseEntity.ok(anbieterService.loadProviders());
    }
}
