package de.skillspot.service;

import de.skillspot.dto.KategorieDto;
import de.skillspot.mapper.KategorieMapper;
import de.skillspot.store.KategorieStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategorieService {
    private final KategorieStore categoryStore;
    private final KategorieMapper kategorieMapper;

    public KategorieService(KategorieStore categoryStore, KategorieMapper kategorieMapper) {
        this.categoryStore = categoryStore;
        this.kategorieMapper = kategorieMapper;
    }

    public List<KategorieDto> loadcategories(){
        return categoryStore.loadcategories().stream().map(kategorieMapper::toDto).toList();
    }
}
