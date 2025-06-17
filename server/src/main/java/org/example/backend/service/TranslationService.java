package org.example.backend.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface TranslationService {
    JsonNode translateJsonNode(JsonNode node, String from, String to);
}