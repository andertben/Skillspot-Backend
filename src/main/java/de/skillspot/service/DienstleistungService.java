package de.skillspot.service;

import de.skillspot.dto.CreateDienstleistungRequest;
import de.skillspot.dto.DienstleistungDto;
import de.skillspot.dto.DienstleistungResponse;
import de.skillspot.entity.AnbieterEntity;
import de.skillspot.entity.BenutzerEntity;
import de.skillspot.entity.DienstleistungEntity;
import de.skillspot.entity.KategorieEntity;
import de.skillspot.mapper.DienstleistungMapper;
import de.skillspot.store.AnbieterStore;
import de.skillspot.store.BenutzerStore;
import de.skillspot.store.DienstleistungStore;
import de.skillspot.store.KategorieStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class DienstleistungService {

    private final DienstleistungStore dienstleistungStore;
    private final DienstleistungMapper dienstleistungMapper;
    private final BenutzerStore benutzerStore;
    private final AnbieterStore anbieterStore;
    private final KategorieStore kategorieStore;

    public DienstleistungService(DienstleistungStore dienstleistungStore,
                                 DienstleistungMapper dienstleistungMapper,
                                 BenutzerStore benutzerStore,
                                 AnbieterStore anbieterStore,
                                 KategorieStore kategorieStore) {
        this.dienstleistungStore = dienstleistungStore;
        this.dienstleistungMapper = dienstleistungMapper;
        this.benutzerStore = benutzerStore;
        this.anbieterStore = anbieterStore;
        this.kategorieStore = kategorieStore;
    }

    public List<DienstleistungDto> loadServices() {
        return dienstleistungStore.loadServices()
                .stream()
                .map(dienstleistungMapper::toDto)
                .toList();
    }

    public List<DienstleistungDto> loadMyServices(String userSub) {
        BenutzerEntity benutzer = benutzerStore.findByAuth0Sub(userSub)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        AnbieterEntity anbieter = anbieterStore.findByBenutzerId(benutzer.getBenutzerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not an Anbieter"));

        return dienstleistungStore.loadByAnbieterId(anbieter.getAnbieterId())
                .stream()
                .map(dienstleistungMapper::toDto)
                .toList();
    }

    public DienstleistungResponse createDienstleistung(String userSub, CreateDienstleistungRequest request) {
        log.info("Creating dienstleistung for userSub: {}", userSub);

        // 1. Find User
        BenutzerEntity benutzer = benutzerStore.findByAuth0Sub(userSub)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 2. Find Anbieter
        AnbieterEntity anbieter = anbieterStore.findByBenutzerId(benutzer.getBenutzerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not an Anbieter"));

        // 3. Validate Request
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }

        // 4. Validate Kategorie
        KategorieEntity kategorie = kategorieStore.findById(request.getKategorieId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kategorie not found"));

        if (kategorie.getOberkategorie_id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must select a sub-category");
        }

        // 5. Save
        DienstleistungEntity entity = DienstleistungEntity.builder()
                .anbieterId(anbieter.getAnbieterId())
                .kategorieId(request.getKategorieId())
                .title(request.getTitle())
                .beschreibung(request.getBeschreibung())
                .build();

        DienstleistungEntity saved = dienstleistungStore.save(entity);
        log.info("Dienstleistung created with ID: {}", saved.getDienstleistungId());

        return DienstleistungResponse.builder()
                .dienstleistungId(saved.getDienstleistungId())
                .title(saved.getTitle())
                .kategorieId(saved.getKategorieId())
                .build();
    }
}
