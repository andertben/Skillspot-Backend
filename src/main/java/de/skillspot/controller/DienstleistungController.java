package de.skillspot.controller;

import de.skillspot.dto.DienstleistungDto;
import de.skillspot.service.DienstleistungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DienstleistungController {

    private final DienstleistungService dienstleistungService;

    public DienstleistungController(DienstleistungService dienstleistungService) {
        this.dienstleistungService = dienstleistungService;
    }

    @GetMapping("/services")
    public ResponseEntity<List<DienstleistungDto>> loadServices() {
        return ResponseEntity.ok(dienstleistungService.loadServices());
    }
}
