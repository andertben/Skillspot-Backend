package de.skillspot.service;

import de.skillspot.dto.DienstleistungDto;
import de.skillspot.mapper.DienstleistungMapper;
import de.skillspot.store.DienstleistungStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DienstleistungService {

    private final DienstleistungStore dienstleistungStore;
    private final DienstleistungMapper dienstleistungMapper;

    public DienstleistungService(DienstleistungStore dienstleistungStore,
                                 DienstleistungMapper dienstleistungMapper) {
        this.dienstleistungStore = dienstleistungStore;
        this.dienstleistungMapper = dienstleistungMapper;
    }

    public List<DienstleistungDto> loadServices() {
        return dienstleistungStore.loadServices()
                .stream()
                .map(dienstleistungMapper::toDto)
                .toList();
    }
}
