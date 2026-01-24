package de.skillspot.service;

import de.skillspot.dto.BewertungDto;
import de.skillspot.dto.CreateBewertungRequest;
import de.skillspot.entity.BenutzerEntity;
import de.skillspot.entity.BewertungEntity;
import de.skillspot.entity.DienstleistungEntity;
import de.skillspot.mapper.BewertungMapper;
import de.skillspot.store.BenutzerStore;
import de.skillspot.store.BewertungStore;
import de.skillspot.store.DienstleistungStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BewertungService {

    private final BewertungStore bewertungStore;
    private final BewertungMapper bewertungMapper;
    private final BenutzerStore benutzerStore;
    private final DienstleistungStore dienstleistungStore;

    public BewertungService(BewertungStore bewertungStore,
                            BewertungMapper bewertungMapper,
                            BenutzerStore benutzerStore,
                            DienstleistungStore dienstleistungStore) {
        this.bewertungStore = bewertungStore;
        this.bewertungMapper = bewertungMapper;
        this.benutzerStore = benutzerStore;
        this.dienstleistungStore = dienstleistungStore;
    }

    public List<BewertungDto> loadReviews() {
        return bewertungStore.loadReviews()
                .stream()
                .map(bewertungMapper::toDto)
                .toList();
    }

    public List<BewertungDto> findByServiceId(Long serviceId) {
        return bewertungStore.findByServiceId(serviceId)
                .stream()
                .map(bewertungMapper::toDto)
                .toList();
    }

    public List<BewertungDto> findByProviderId(Long providerId) {
        return bewertungStore.findByProviderId(providerId)
                .stream()
                .map(bewertungMapper::toDto)
                .toList();
    }

    public Double getAverageRatingByServiceId(Long serviceId) {
        return bewertungStore.getAverageRatingByServiceId(serviceId);
    }

    public BewertungDto createBewertung(String userSub, CreateBewertungRequest request) {
        // 1. Find User
        BenutzerEntity benutzer = benutzerStore.findByAuth0Sub(userSub)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 2. Find Dienstleistung to get AnbieterId
        DienstleistungEntity dienstleistung = dienstleistungStore.findById(request.getDienstleistungId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dienstleistung not found"));

        // 3. Prepare Entity
        BewertungEntity entity = BewertungEntity.builder()
                .dienstleistungId(request.getDienstleistungId())
                .benutzerId(benutzer.getBenutzerId())
                .anbieterId(dienstleistung.getAnbieterId())
                .bewertung(request.getBewertung())
                .text(request.getText())
                .build();

        // 4. Save
        Long newId = bewertungStore.save(entity);

        // 5. Load and return as DTO
        return bewertungStore.findById(newId)
                .map(bewertungMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Failed to load newly created review"));
    }
}
