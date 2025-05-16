package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Translation;
import org.example.backend.repository.ITranslationRepository;
import org.example.backend.service.ITranslationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationService implements ITranslationService {
    private final ITranslationRepository translationRepository;

    @Override
    public Map<Integer, Map<String, String>> getTranslations(String entityType, List<Integer> entityIds, String languageCode) {
        List<Translation> translations = translationRepository
                .findByEntityTypeAndEntityIdInAndLanguageCode(entityType, entityIds, languageCode);

        return translations.stream()
                .collect(Collectors.groupingBy(Translation::getEntityId,
                        Collectors.toMap(Translation::getFieldName, Translation::getTranslatedValue)));
    }

    @Override
    public Map<String, String> getTranslation(String entityType, Integer entityId, String languageCode) {
        List<Translation> translations = translationRepository
                .findByEntityTypeAndEntityIdAndLanguageCode(entityType, entityId, languageCode);

        return translations.stream()
                .collect(Collectors.toMap(Translation::getFieldName, Translation::getTranslatedValue));
    }

    @Override
    public void saveTranslation(String entityType, Integer entityId, String fieldName, String languageCode, String value) {
        Translation translation = new Translation();
        translation.setEntityType(entityType);
        translation.setEntityId(entityId);
        translation.setFieldName(fieldName);
        translation.setLanguageCode(languageCode);
        translation.setTranslatedValue(value);

        translationRepository.save(translation);
    }
}
