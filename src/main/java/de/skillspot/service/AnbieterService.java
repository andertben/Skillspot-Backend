package de.skillspot.service;

import de.skillspot.dto.AnbieterDto;
import de.skillspot.mapper.AnbieterMapper;
import de.skillspot.store.AnbieterStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnbieterService {

    private final AnbieterStore anbieterStore;
    private final AnbieterMapper anbieterMapper;

    public AnbieterService(AnbieterStore anbieterStore, AnbieterMapper anbieterMapper) {
        this.anbieterStore = anbieterStore;
        this.anbieterMapper = anbieterMapper;
    }

    public List<AnbieterDto> loadProviders() {
        return anbieterStore.loadProviders()
                .stream()
                .map(anbieterMapper::toDto)
                .toList();
    }
}
