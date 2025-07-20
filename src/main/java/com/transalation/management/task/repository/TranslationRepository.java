package com.transalation.management.task.repository;

import com.transalation.management.task.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

    List<Translation> findByTag(String tag);

    List<Translation> findByTranslationKeyContainingIgnoreCase(String key);

    List<Translation> findByContentContainingIgnoreCase(String content);
}
