package de.skillspot.service;

import de.skillspot.dto.BuchungDto;
import de.skillspot.mapper.BuchungMapper;
import de.skillspot.store.BuchungStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuchungService {

    private final BuchungStore buchungStore;
    private final BuchungMapper buchungMapper;

    public BuchungService(BuchungStore buchungStore, BuchungMapper buchungMapper) {
        this.buchungStore = buchungStore;
        this.buchungMapper = buchungMapper;
    }

    public List<BuchungDto> loadBookings() {
        return buchungStore.loadBookings()
                .stream()
                .map(buchungMapper::toDto)
                .toList();
    }
}
