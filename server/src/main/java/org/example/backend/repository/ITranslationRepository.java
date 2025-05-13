package org.example.backend.repository;

import org.example.backend.domain.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITranslationRepository extends JpaRepository<Translation, Integer> {
    List<Translation> findByEntityTypeAndEntityIdAndLanguageCode(
            String entityType, Integer entityId, String languageCode);

    List<Translation> findByEntityTypeAndEntityIdInAndLanguageCode(
            String entityType, List<Integer> entityIds, String languageCode);
}