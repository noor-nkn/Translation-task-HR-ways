package com.transalation.management.task.service;

import com.transalation.management.task.dto.TranslationDTO;
import com.transalation.management.task.entity.Language;
import com.transalation.management.task.entity.Translation;
import com.transalation.management.task.repository.LanguageRepository;
import com.transalation.management.task.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TranslationService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private TranslationRepository translationRepository;


    public Language addOrUpdateTranslation(TranslationDTO translationRecord) {
        Optional<Language> optionalLanguage = languageRepository.findByLocale(translationRecord.getLocale());
        Language language;

        if (optionalLanguage.isPresent()) {
            language = optionalLanguage.get();
        } else {
            // Create new language record if not found
            language = new Language();
            language.setLocale(translationRecord.getLocale());
            language.setEntries(new ArrayList<>());
        }
        // Check if an entry with the same key exists
        Optional<Translation> existingEntryOpt = language.getEntries().stream()
                .filter(entry -> entry.getTranslationKey().equals(translationRecord.getKey()))
                .findFirst();

        if (existingEntryOpt.isPresent()) {
            // Update the existing entry
            Translation existingEntry = existingEntryOpt.get();
            existingEntry.setContent(translationRecord.getContent());
            existingEntry.setTag(translationRecord.getTag());
        } else {
            // Add new entry
            Translation newEntry = new Translation();
            newEntry.setTranslationKey(translationRecord.getKey());
            newEntry.setContent(translationRecord.getContent());
            newEntry.setTag(translationRecord.getTag());
            newEntry.setLanguage(language);
            language.getEntries().add(newEntry);
        }

        // Save=ing the translation record
        return languageRepository.save(language);
    }

    public Optional<Translation> getTranslationsById(Long id) {
        return translationRepository.findById(id);
    }

    public List<Translation> searchByTag(String tag) {
        return translationRepository.findByTag(tag);
    }

    public List<Translation> searchByKey(String key) {
        return translationRepository.findByTranslationKeyContainingIgnoreCase(key);
    }

    public List<Translation> searchByContent(String content) {
        return translationRepository.findByContentContainingIgnoreCase(content);
    }

    public  Map<String, Map<String, String>> exportTranslations(String locale, int page,int size) {
        Map<String, Map<String, String>> result = new HashMap<>();

        Pageable pageable = PageRequest.of(page, size);

        if ("all".equalsIgnoreCase(locale)) {
            Page<Language> languages = languageRepository.findAll(pageable);

            for (Language language : languages) {
                Map<String, String> map = new HashMap<>();
                for (Translation entry : language.getEntries()) {
                    map.put(entry.getTranslationKey(), entry.getContent());
                }
                result.put(language.getLocale(), map);
            }

        } else {
            Page<Language> languagePage = languageRepository.findByLocale(locale, pageable);

            if (!languagePage.isEmpty()) {
                Map<String, String> map = new HashMap<>();
                for (Language language : languagePage.getContent()) {
                    for (Translation entry : language.getEntries()) {
                        map.put(entry.getTranslationKey(), entry.getContent());
                    }
                }
                result.put(locale, map);
            }
        }

        return result;
    }
}

