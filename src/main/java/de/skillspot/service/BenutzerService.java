package de.skillspot.service;

import de.skillspot.dto.BenutzerDto;
import de.skillspot.mapper.BenutzerMapper;
import de.skillspot.store.BenutzerStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenutzerService {

    private final BenutzerStore benutzerStore;
    private final BenutzerMapper benutzerMapper;

    public BenutzerService(BenutzerStore benutzerStore, BenutzerMapper benutzerMapper) {
        this.benutzerStore = benutzerStore;
        this.benutzerMapper = benutzerMapper;
    }

    public List<BenutzerDto> loadUsers() {
        return benutzerStore.loadUsers()
                .stream()
                .map(benutzerMapper::toDto)
                .toList();
    }
}
