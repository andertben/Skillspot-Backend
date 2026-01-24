package de.skillspot.controller;

import de.skillspot.dto.CreateDienstleistungRequest;
import de.skillspot.dto.DienstleistungDto;
import de.skillspot.dto.DienstleistungResponse;
import de.skillspot.service.DienstleistungService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/services", "/dienstleistungen"})
public class DienstleistungController {

    private final DienstleistungService dienstleistungService;

    public DienstleistungController(DienstleistungService dienstleistungService) {
        this.dienstleistungService = dienstleistungService;
    }

    @Operation(summary = "Get all services", description = "Aliases: /services, /dienstleistungen")
    @GetMapping
    public ResponseEntity<List<DienstleistungDto>> loadServices() {
        return ResponseEntity.ok(dienstleistungService.loadServices());
    }

    @Operation(summary = "Get my services (Anbieter only)", description = "Aliases: /services/my, /dienstleistungen/my")
    @GetMapping("/my")
    public ResponseEntity<List<DienstleistungDto>> loadMyServices(JwtAuthenticationToken auth) {
        String userSub = auth.getToken().getClaimAsString("sub");
        return ResponseEntity.ok(dienstleistungService.loadMyServices(userSub));
    }

    @Operation(summary = "Create a new service (Anbieter only)", description = "Aliases: /services, /dienstleistungen")
    @PostMapping
    public ResponseEntity<DienstleistungResponse> createDienstleistung(
            @RequestBody CreateDienstleistungRequest request,
            JwtAuthenticationToken auth) {
        String userSub = auth.getToken().getClaimAsString("sub");
        return ResponseEntity.ok(dienstleistungService.createDienstleistung(userSub, request));
    }
}
