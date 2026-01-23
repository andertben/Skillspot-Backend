package de.skillspot.service;

import de.skillspot.dto.BewertungDto;
import de.skillspot.mapper.BewertungMapper;
import de.skillspot.store.BewertungStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BewertungService {

    private final BewertungStore bewertungStore;
    private final BewertungMapper bewertungMapper;

    public BewertungService(BewertungStore bewertungStore, BewertungMapper bewertungMapper) {
        this.bewertungStore = bewertungStore;
        this.bewertungMapper = bewertungMapper;
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
}
