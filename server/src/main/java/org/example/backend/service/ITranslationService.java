package org.example.backend.service;

import java.util.List;
import java.util.Map;

public interface ITranslationService {
    Map<Integer, Map<String, String>> getTranslations(
            String entityType, List<Integer> entityIds, String languageCode);

    Map<String, String> getTranslation(String entityType, Integer entityId, String languageCode);

    void saveTranslation(String entityType, Integer entityId,
                                String fieldName, String languageCode, String value);
}
