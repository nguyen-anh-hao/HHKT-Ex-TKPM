package org.example.backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.TranslateRequest;
import org.example.backend.dto.response.TranslateResponse;
import org.example.backend.service.TranslationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class TranslationServiceImpl implements TranslationService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public TranslationServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:5000")
                .build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    private String translateText(String text, String from, String to) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        try {
            TranslateRequest request = new TranslateRequest();
            request.setQ(text);
            request.setSource(from);
            request.setTarget(to);

            TranslateResponse response = webClient.post()
                    .uri("/translate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(TranslateResponse.class)
                    .block();

            return response != null ? response.getTranslatedText() : text;
        } catch (Exception e) {
            return text;
        }
    }

    @Override
    public JsonNode translateJsonNode(JsonNode node, String from, String to) {
        log.info("Translating JSON node from '{}' to '{}'", from, to);
        return translateJsonNodeInternal(node, from, to);
    }

    private JsonNode translateJsonNodeInternal(JsonNode node, String from, String to) {

        if (node == null) {
            return null;
        }

        if (node.isTextual()) {
            String originalText = node.asText();
            if (originalText != null && !originalText.trim().isEmpty() && !isNonTranslatableText(originalText)) {
                String translatedText = translateText(originalText, from, to);
                return new TextNode(translatedText);
            }
            return node;
        } else if (node.isObject()) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            node.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = translateJsonNodeInternal(entry.getValue(), from, to);
                objectNode.set(key, value);
            });
            return objectNode;
        } else if (node.isArray()) {
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for (JsonNode item : node) {
                arrayNode.add(translateJsonNodeInternal(item, from, to));
            }
            return arrayNode;
        }

        return node;
    }

    private boolean isNonTranslatableText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return true;
        }

        String trimmed = text.trim();
        return trimmed.matches("^[0-9]+$") ||                            // Numbers
                trimmed.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") || // Email
                trimmed.matches("^https?://.*") ||                       // URL
                trimmed.matches("^[A-Z0-9_]+$") ||                       // Uppercase ENUM
                trimmed.matches("^\\d{4}-\\d{2}-\\d{2}$") ||             // Date in yyyy-MM-dd format
                trimmed.matches("^\\d{4}-\\d{2}-\\d{2}T.*") ||           // ISO 8601 DateTime
                trimmed.matches("^\\+?[0-9]{7,15}$") ||                  // phone number
                trimmed.length() < 2;                                          // String too short
    }
}