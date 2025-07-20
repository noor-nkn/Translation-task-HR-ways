package com.transalation.management.task.controller;

import com.transalation.management.task.dto.TranslationDTO;
import com.transalation.management.task.entity.Translation;
import com.transalation.management.task.service.TranslationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/translations")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping("")
    public ResponseEntity<?> addOrUpdate(@RequestBody TranslationDTO entry) {
        translationService.addOrUpdateTranslation( entry);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslation(@PathVariable Long id) {
        return translationService.getTranslationsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Translation> search(@RequestParam Optional<String> tag,
                                    @RequestParam Optional<String> key,
                                    @RequestParam Optional<String> content) {
        if (tag.isPresent()) {
            return translationService.searchByTag(tag.get());
        } else if (key.isPresent()) {
            return translationService.searchByKey(key.get());
        } else if (content.isPresent()) {
            return translationService.searchByContent(content.get());
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/export/{locale}")
    public ResponseEntity<Map<String, Map<String, String>>> export(@PathVariable String locale,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "50") int size) {

        long start = System.currentTimeMillis();

        Map<String, Map<String, String>> translations = translationService.exportTranslations(locale, page, size);

        long end = System.currentTimeMillis();
        long timeTaken = end - start;

        if (timeTaken > 500) {
            log.warn("Pagination Export took " + timeTaken + "ms");
        }

        return ResponseEntity.ok(translations);
    }
}

