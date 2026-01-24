package de.skillspot.controller;

import de.skillspot.dto.KategorieDto;
import de.skillspot.dto.KategorieTreeDto;
import de.skillspot.service.KategorieService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kategorien")
@Slf4j
public class KategorieController {
    private final KategorieService kategorieService;

    public KategorieController(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseEntity<List<KategorieDto>> loadcategories(
            @RequestParam(name = "lang", required = false) String lang,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLang) {
        String effectiveLang = (lang != null) ? lang : acceptLang;
        String normalizedLang = normalizeLang(effectiveLang);
        log.info("Loading categories for language: {} (input: lang={}, acceptLang={})", normalizedLang, lang, acceptLang);
        return ResponseEntity.ok(kategorieService.loadcategories(normalizedLang));
    }

    @Operation(summary = "Get category tree (grouped by Oberkategorien)")
    @GetMapping("/tree")
    public ResponseEntity<List<KategorieTreeDto>> getCategoryTree(
            @RequestParam(name = "lang", required = false) String lang,
            @RequestHeader(name = "Accept-Language", required = false) String acceptLang) {
        String effectiveLang = (lang != null) ? lang : acceptLang;
        String normalizedLang = normalizeLang(effectiveLang);
        log.info("Loading category tree for language: {} (input: lang={}, acceptLang={})", normalizedLang, lang, acceptLang);
        return ResponseEntity.ok(kategorieService.getCategoryTree(normalizedLang));
    }

    private String normalizeLang(String lang) {
        if (lang == null || lang.length() < 2) {
            return "de";
        }
        String shortLang = lang.substring(0, 2).toLowerCase();
        if ("en".equals(shortLang)) {
            return "en";
        }
        return "de";
    }
}
