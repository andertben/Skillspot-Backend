package de.skillspot.controller;

import de.skillspot.dto.BuchungDto;
import de.skillspot.service.BuchungService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuchungController {

    private final BuchungService buchungService;

    public BuchungController(BuchungService buchungService) {
        this.buchungService = buchungService;
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BuchungDto>> loadBookings() {
        return ResponseEntity.ok(buchungService.loadBookings());
    }
}
