package com.transalation.management.task.config;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;
import com.transalation.management.task.entity.Language;
import com.transalation.management.task.entity.Translation;
import com.transalation.management.task.repository.LanguageRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(LanguageRepository languageRepository) {
        return args -> {
            Faker faker = new Faker();
            Random random = new Random();

            int batchSize = 1000;
            int totalRecords = 100000;

            for (int i = 0; i < totalRecords / batchSize; i++) {
                ArrayList<Language> batchLanguages = new ArrayList<>();

                for (int j = 0; j < batchSize; j++) {
                    Language language = new Language();
                    language.setLocale("en");

                    ArrayList<Translation> entries = new ArrayList<>();
                    for (int k = 0; k < 5; k++) {  // 5 translations per language
                        Translation t = new Translation();
                        t.setTranslationKey("key-" + faker.lorem().word() + random.nextInt(10000));
                        t.setContent(faker.lorem().sentence());
                        t.setTag("tag-" + faker.lorem().word());
                        entries.add(t);
                    }

                    language.setEntries(entries);
                    entries.forEach(t -> t.setLanguage(language));  // bi-directional mapping
                    batchLanguages.add(language);
                }

                languageRepository.saveAll(batchLanguages);
                System.out.println("Inserted batch " + (i + 1));
            }

            System.out.println("Data seeding complete: " + totalRecords + " translations inserted.");
        };
    }
}

