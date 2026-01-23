package de.skillspot.service;

import de.skillspot.dto.BenutzerDto;
import de.skillspot.dto.CompleteProfileRequest;
import de.skillspot.dto.GeocodingResult;
import de.skillspot.dto.UpdateProfileRequest;
import de.skillspot.entity.BenutzerEntity;
import de.skillspot.mapper.BenutzerMapper;
import de.skillspot.store.BenutzerStore;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BenutzerService {

    private final BenutzerStore benutzerStore;
    private final BenutzerMapper benutzerMapper;
    private final RestTemplate restTemplate;

    public BenutzerService(BenutzerStore benutzerStore, BenutzerMapper benutzerMapper) {
        this.benutzerStore = benutzerStore;
        this.benutzerMapper = benutzerMapper;
        this.restTemplate = new RestTemplate();
    }

    public List<BenutzerDto> loadUsers() {
        return benutzerStore.loadUsers()
                .stream()
                .map(benutzerMapper::toDto)
                .toList();
    }

    public Optional<BenutzerEntity> findByAuth0Sub(String sub) {
        return benutzerStore.findByAuth0Sub(sub);
    }

    public void completeProfile(String sub, CompleteProfileRequest req) {
        benutzerStore.upsertByAuth0Sub(sub, req.getDisplayName(), req.getRole(), null, req.getLocationLat(), req.getLocationLon());
    }

    public void updateProfile(String sub, UpdateProfileRequest req) {
        if ("PROVIDER".equals(req.getRole()) && (req.getAddress() == null || req.getAddress().isBlank())) {
            throw new IllegalArgumentException("Address is required for PROVIDER");
        }

        BigDecimal lat = null;
        BigDecimal lon = null;

        if ("PROVIDER".equals(req.getRole()) && req.getAddress() != null && !req.getAddress().isBlank()) {
            GeocodingResult result = geocodeAddress(req.getAddress());
            lat = new BigDecimal(result.getLat());
            lon = new BigDecimal(result.getLon());
        }

        benutzerStore.upsertByAuth0Sub(
                sub,
                req.getDisplayName(),
                req.getRole(),
                req.getAddress(),
                lat,
                lon
        );
    }

    private GeocodingResult geocodeAddress(String address) {
        String url = UriComponentsBuilder.fromUriString("https://nominatim.openstreetmap.org/search")
                .queryParam("format", "json")
                .queryParam("q", address)
                .queryParam("limit", 1)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "skillspot-app");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GeocodingResult[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GeocodingResult[].class
        );

        if (response.getBody() == null || response.getBody().length == 0) {
            throw new IllegalArgumentException("Address not found");
        }

        return response.getBody()[0];
    }

    public boolean isProfileComplete(String sub) {
        return benutzerStore.findByAuth0Sub(sub)
                .map(b -> b.getRolle() != null && b.getDisplayName() != null)
                .orElse(false);
    }
}
