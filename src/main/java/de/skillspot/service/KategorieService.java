package de.skillspot.service;

import de.skillspot.dto.KategorieChildDto;
import de.skillspot.dto.KategorieDto;
import de.skillspot.dto.KategorieTreeDto;
import de.skillspot.entity.KategorieEntity;
import de.skillspot.mapper.KategorieMapper;
import de.skillspot.store.KategorieStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<KategorieTreeDto> getCategoryTree() {
        List<KategorieEntity> allCategories = categoryStore.loadcategories();

        // Map parentId to list of children
        Map<Long, List<KategorieEntity>> childrenByParent = allCategories.stream()
                .filter(k -> k.getOberkategorie_id() != null)
                .collect(Collectors.groupingBy(KategorieEntity::getOberkategorie_id));

        return allCategories.stream()
                .filter(k -> k.getOberkategorie_id() == null)
                .map(parent -> KategorieTreeDto.builder()
                        .id(parent.getKategorie_id())
                        .name(parent.getBezeichnung())
                        .children(childrenByParent.getOrDefault(parent.getKategorie_id(), List.of()).stream()
                                .map(child -> KategorieChildDto.builder()
                                        .id(child.getKategorie_id())
                                        .name(child.getBezeichnung())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }
}
