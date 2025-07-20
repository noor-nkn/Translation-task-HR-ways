package com.transalation.management.task.repository;

import com.transalation.management.task.entity.Language;
import com.transalation.management.task.entity.Translation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Page<Language> findByLocale(String locale, Pageable pageable);

    Optional<Language> findByLocale(String locale);
}
