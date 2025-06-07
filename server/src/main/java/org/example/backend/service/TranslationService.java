package org.example.backend.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface TranslationService {
    String translateText(String text, String from, String to);
    Object translateObject(Object obj, String from, String to);
    JsonNode translateJsonNode(JsonNode node, String from, String to);
}