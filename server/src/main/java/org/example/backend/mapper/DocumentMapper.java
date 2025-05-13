package org.example.backend.mapper;

import org.example.backend.domain.Document;
import org.example.backend.dto.request.DocumentRequest;
import org.example.backend.dto.response.DocumentResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DocumentMapper {
    public static Document mapToDomain(DocumentRequest documentRequest) {
        return Document.builder()
                .documentType(documentRequest.getDocumentType())
                .documentNumber(documentRequest.getDocumentNumber())
                .issuedDate(documentRequest.getIssuedDate())
                .expiredDate(documentRequest.getExpiredDate())
                .issuedBy(documentRequest.getIssuedBy())
                .issuedCountry(documentRequest.getIssuedCountry())
                .note(documentRequest.getNote())
                .hasChip(documentRequest.getHasChip())
                .build();
    }

    public static DocumentResponse mapToResponse(Document document) {
        return DocumentResponse.builder()
                .documentType(document.getDocumentType())
                .documentNumber(document.getDocumentNumber())
                .issuedDate(document.getIssuedDate())
                .expiredDate(document.getExpiredDate())
                .issuedBy(document.getIssuedBy())
                .issuedCountry(document.getIssuedCountry())
                .note(document.getNote())
                .hasChip(document.getHasChip())
                .build();
    }

    public static DocumentResponse mapToResponseWithTranslation(
            Document document,
            Map<String, String> translations) {

        return DocumentResponse.builder()
                .documentType(translations.getOrDefault("documentType", document.getDocumentType()))
                .documentNumber(document.getDocumentNumber())
                .issuedDate(document.getIssuedDate())
                .expiredDate(document.getExpiredDate())
                .issuedBy(translations.getOrDefault("issuedBy", document.getIssuedBy()))
                .issuedCountry(document.getIssuedCountry())
                .note(translations.getOrDefault("note", document.getNote()))
                .hasChip(document.getHasChip())
                .build();
    }
}
