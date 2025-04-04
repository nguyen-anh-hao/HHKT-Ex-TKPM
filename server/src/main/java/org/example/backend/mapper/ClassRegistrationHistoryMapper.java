package org.example.backend.mapper;

import jakarta.validation.constraints.Null;
import org.apache.commons.lang3.ObjectUtils;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.ClassRegistrationHistory;
import org.example.backend.dto.request.ClassRegistrationHistoryRequest;
import org.example.backend.dto.response.ClassRegistrationHistoryResponse;
import org.springframework.stereotype.Component;

@Component
public class ClassRegistrationHistoryMapper {
    public static ClassRegistrationHistoryResponse mapFromDomainToClassRegistrationHistoryResponse(ClassRegistrationHistory classRegistrationHistory) {
       return ClassRegistrationHistoryResponse.builder()
                .id(classRegistrationHistory.getId())
                .action(classRegistrationHistory.getAction())
                .reason(classRegistrationHistory.getReason())
                .classRegistrationId(classRegistrationHistory.getClassRegistration().getId())
                .classCode(classRegistrationHistory.getClassRegistration().getAClass().getClassCode())
                .studentId(classRegistrationHistory.getClassRegistration().getStudent().getStudentId())
                .createdBy(classRegistrationHistory.getCreatedBy())
                .updatedBy(classRegistrationHistory.getUpdatedBy())
                .createdAt(classRegistrationHistory.getCreatedAt().toString())
                .updatedAt(classRegistrationHistory.getUpdatedAt().toString())
                .build();

    }

    public static ClassRegistrationHistory mapFromClassRegistrationHistoryRequestToDomain(ClassRegistrationHistoryRequest classRegistrationHistoryRequest) {
        return ClassRegistrationHistory.builder()
                .action(classRegistrationHistoryRequest.getAction())
                .reason(classRegistrationHistoryRequest.getReason())
                .build();
    }

    public static ClassRegistrationHistoryRequest mapFromClassRegistrationDomainToClassRegistrationHistoryRequest(ClassRegistration classRegistration) {
        return ClassRegistrationHistoryRequest.builder()
                .action(classRegistration.getStatus())
                .classRegistrationId(classRegistration.getId())
                .build();
    }
}
