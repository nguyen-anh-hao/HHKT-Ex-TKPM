package org.example.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backend.service.impl.TranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    private TranslationServiceImpl translationService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Create a spy of the service to mock the private translateText method
        translationService = spy(new TranslationServiceImpl());

        // Mock the translateText method to simulate translation
        Mockito.lenient().doAnswer(invocation -> {
            String text = invocation.getArgument(0);
            String from = invocation.getArgument(1);
            String to = invocation.getArgument(2);

            if ("en".equals(from) && "vi".equals(to)) {
                return translateToVietnamese(text);
            }
            return text;
        }).when(translationService).translateText(anyString(), anyString(), anyString());
    }

    private String translateToVietnamese(String text) {
        // Simple mock translations for testing
        switch (text) {
            case "Hello World":
                return "Xin chào thế giới";
            case "John Doe":
                return "John Doe"; // Names typically don't translate
            case "Software Engineer":
                return "Kỹ sư phần mềm";
            case "Good morning":
                return "Chào buổi sáng";
            case "Good evening":
                return "Chào buổi tối";
            case "Welcome":
                return "Chào mừng";
            case "Main Street":
                return "Phố chính";
            case "New York":
                return "New York";
            case "Hello there":
                return "Xin chào";
            case "Computer Science":
                return "Khoa học máy tính";
            case "Bachelor of Computer Science":
                return "Cử nhân Khoa học máy tính";
            default:
                return text;
        }
    }

    @Test
    void translateJsonNode_WithSimpleTextNode_ShouldTranslateSuccessfully() throws Exception {
        // Given
        String originalText = "Hello World";
        JsonNode textNode = objectMapper.valueToTree(originalText);

        // When
        JsonNode result = translationService.translateJsonNode(textNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isTextual());
        assertEquals("Xin chào thế giới", result.asText());
    }

    @Test
    void translateJsonNode_WithObjectNode_ShouldTranslateAllTextFields() throws Exception {
        // Given
        ObjectNode originalNode = objectMapper.createObjectNode();
        originalNode.put("name", "John Doe");
        originalNode.put("description", "Software Engineer");
        originalNode.put("age", 25);
        originalNode.put("email", "john@example.com");

        // When
        JsonNode result = translationService.translateJsonNode(originalNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isObject());
        assertEquals("John Doe", result.get("name").asText());
        assertEquals("Kỹ sư phần mềm", result.get("description").asText());
        assertEquals(25, result.get("age").asInt());
        assertEquals("john@example.com", result.get("email").asText()); // Email should not be translated
    }

    @Test
    void translateJsonNode_WithArrayNode_ShouldTranslateAllElements() throws Exception {
        // Given
        ArrayNode originalArray = objectMapper.createArrayNode();
        originalArray.add("Good morning");
        originalArray.add("Good evening");
        originalArray.add("123"); // Number should not be translated

        // When
        JsonNode result = translationService.translateJsonNode(originalArray, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isArray());
        assertEquals(3, result.size());
        assertEquals("Chào buổi sáng", result.get(0).asText());
        assertEquals("Chào buổi tối", result.get(1).asText());
        assertEquals("123", result.get(2).asText()); // Numbers should not be translated
    }

    @Test
    void translateJsonNode_WithNestedObject_ShouldTranslateNestedFields() throws Exception {
        // Given
        ObjectNode originalNode = objectMapper.createObjectNode();
        originalNode.put("title", "Welcome");

        ObjectNode addressNode = objectMapper.createObjectNode();
        addressNode.put("street", "Main Street");
        addressNode.put("city", "New York");
        originalNode.set("address", addressNode);

        // When
        JsonNode result = translationService.translateJsonNode(originalNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isObject());
        assertEquals("Chào mừng", result.get("title").asText());

        JsonNode addressResult = result.get("address");
        assertNotNull(addressResult);
        assertTrue(addressResult.isObject());
        assertEquals("Phố chính", addressResult.get("street").asText());
        assertEquals("New York", addressResult.get("city").asText());
    }

    @Test
    void translateJsonNode_WithNonTranslatableContent_ShouldSkipTranslation() throws Exception {
        // Given
        ObjectNode originalNode = objectMapper.createObjectNode();
        originalNode.put("email", "user@example.com");
        originalNode.put("phone", "+84123456789");
        originalNode.put("url", "https://example.com");
        originalNode.put("date", "2024-01-15");
        originalNode.put("status", "ACTIVE");
        originalNode.put("id", "12345");

        // When
        JsonNode result = translationService.translateJsonNode(originalNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isObject());
        assertEquals("user@example.com", result.get("email").asText());
        assertEquals("+84123456789", result.get("phone").asText());
        assertEquals("https://example.com", result.get("url").asText());
        assertEquals("2024-01-15", result.get("date").asText());
        assertEquals("ACTIVE", result.get("status").asText());
        assertEquals("12345", result.get("id").asText());
    }

    @Test
    void translateJsonNode_WithMixedContent_ShouldTranslateOnlyTranslatableText() throws Exception {
        // Given
        ObjectNode originalNode = objectMapper.createObjectNode();
        originalNode.put("message", "Hello there");
        originalNode.put("email", "test@example.com");
        originalNode.put("count", 42);
        originalNode.put("isActive", true);

        // When
        JsonNode result = translationService.translateJsonNode(originalNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isObject());
        assertEquals("Xin chào", result.get("message").asText());
        assertEquals("test@example.com", result.get("email").asText());
        assertEquals(42, result.get("count").asInt());
        assertTrue(result.get("isActive").asBoolean());
    }

    @Test
    void translateJsonNode_WithNullNode_ShouldReturnNull() {
        // Given
        JsonNode nullNode = null;

        // When
        JsonNode result = translationService.translateJsonNode(nullNode, "en", "vi");

        // Then
        assertNull(result);
    }

    @Test
    void translateJsonNode_WithEmptyString_ShouldReturnOriginal() throws Exception {
        // Given
        JsonNode emptyStringNode = objectMapper.valueToTree("");

        // When
        JsonNode result = translationService.translateJsonNode(emptyStringNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isTextual());
        assertEquals("", result.asText());
    }

    @Test
    void translateJsonNode_WithComplexStudentData_ShouldTranslateAppropriateFields() throws Exception {
        // Given
        ObjectNode studentNode = objectMapper.createObjectNode();
        studentNode.put("studentId", "ST001");
        studentNode.put("fullName", "John Doe");
        studentNode.put("email", "john.doe@student.edu");
        studentNode.put("faculty", "Computer Science");
        studentNode.put("program", "Bachelor of Computer Science");
        studentNode.put("status", "ACTIVE");

        // When
        JsonNode result = translationService.translateJsonNode(studentNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isObject());
        assertEquals("ST001", result.get("studentId").asText()); // ID should not change
        assertEquals("John Doe", result.get("fullName").asText()); // Name might not change
        assertEquals("john.doe@student.edu", result.get("email").asText()); // Email should not change
        assertEquals("Khoa học máy tính", result.get("faculty").asText());
        assertEquals("Cử nhân Khoa học máy tính", result.get("program").asText());
        assertEquals("ACTIVE", result.get("status").asText()); // Status enum should not change
    }

    @Test
    void translateJsonNode_WithNumberNode_ShouldReturnOriginal() throws Exception {
        // Given
        JsonNode numberNode = objectMapper.valueToTree(42);

        // When
        JsonNode result = translationService.translateJsonNode(numberNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isNumber());
        assertEquals(42, result.asInt());
    }

    @Test
    void translateJsonNode_WithBooleanNode_ShouldReturnOriginal() throws Exception {
        // Given
        JsonNode booleanNode = objectMapper.valueToTree(true);

        // When
        JsonNode result = translationService.translateJsonNode(booleanNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isBoolean());
        assertTrue(result.asBoolean());
    }

    @Test
    void translateJsonNode_WithShortText_ShouldNotTranslate() throws Exception {
        // Given
        JsonNode shortTextNode = objectMapper.valueToTree("a"); // Single character

        // When
        JsonNode result = translationService.translateJsonNode(shortTextNode, "en", "vi");

        // Then
        assertNotNull(result);
        assertTrue(result.isTextual());
        assertEquals("a", result.asText()); // Should not translate short text
    }
}