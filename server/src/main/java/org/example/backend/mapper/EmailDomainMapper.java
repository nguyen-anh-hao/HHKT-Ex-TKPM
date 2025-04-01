package org.example.backend.mapper;

import org.example.backend.domain.EmailDomain;
import org.example.backend.dto.request.EmailDomainRequest;
import org.example.backend.dto.response.EmailDomainResponse;

public class EmailDomainMapper {
    public static EmailDomain mapToDomain(EmailDomainRequest request) {
        return EmailDomain.builder()
                .domain(request.getDomain())
                .build();
    }

    public static EmailDomainResponse mapToResponse(EmailDomain emailDomain) {
        return EmailDomainResponse.builder()
                .id(emailDomain.getId())
                .domain(emailDomain.getDomain())
                .createdAt(emailDomain.getCreatedAt())
                .updatedAt(emailDomain.getUpdatedAt())
                .createdBy(emailDomain.getCreatedBy())
                .updatedBy(emailDomain.getUpdatedBy())
                .build();
    }
}
