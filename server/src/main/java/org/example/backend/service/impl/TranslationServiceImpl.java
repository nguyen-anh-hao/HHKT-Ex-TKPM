package org.example.backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backend.dto.request.TranslateRequest;
import org.example.backend.dto.response.TranslateResponse;
import org.example.backend.service.TranslationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public TranslationServiceImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:5000")
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public String translateText(String text, String from, String to) {
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
            System.err.println("Translation failed: " + e.getMessage());
            return text;
        }
    }

    // Method mới trả về JsonNode thay vì Object
    public JsonNode translateJsonNode(JsonNode node, String from, String to) {
        return translateJsonNodeInternal(node, from, to);
    }

    // Method cũ để tương thích ngược
    public Object translateObject(Object obj, String from, String to) {
        try {
            JsonNode node;
            if (obj instanceof JsonNode) {
                node = (JsonNode) obj;
            } else {
                String json = objectMapper.writeValueAsString(obj);
                node = objectMapper.readTree(json);
            }

            JsonNode translatedNode = translateJsonNodeInternal(node, from, to);
            return objectMapper.treeToValue(translatedNode, Object.class);
        } catch (Exception e) {
            System.err.println("Object translation failed: " + e.getMessage());
            return obj;
        }
    }

    private JsonNode translateJsonNodeInternal(JsonNode node, String from, String to) {
        if (node == null) {
            return null;
        }

        if (node.isTextual()) {
            String originalText = node.asText();
            // Chỉ translate nếu text không rỗng và có ý nghĩa
            if (originalText != null && !originalText.trim().isEmpty() &&
                    !isNonTranslatableText(originalText)) {
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

    // Helper method để kiểm tra text có nên translate không
    private boolean isNonTranslatableText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return true;
        }

        // Không translate các giá trị như email, URL, số, v.v.
        String trimmed = text.trim();
        return trimmed.matches("^[0-9]+$") || // Chỉ số
                trimmed.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") || // Email
                trimmed.matches("^https?://.*") || // URL
                trimmed.matches("^[A-Z0-9_]+$") || // Constants/Enum values
                trimmed.length() < 2; // Text quá ngắn
    }
}