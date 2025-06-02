package org.example.backend.mapper;

import org.example.backend.domain.ClassRegistration;
import org.example.backend.dto.request.ClassRegistrationRequest;
import org.example.backend.dto.response.ClassRegistrationResponse;
import org.springframework.stereotype.Component;

@Component
public class ClassRegistrationMapper {
    public static ClassRegistrationResponse mapFromDomainToClassRegistrationResponse(ClassRegistration classRegistration) {
        return ClassRegistrationResponse.builder()
                .id(classRegistration.getId())
                .status(classRegistration.getStatus())
                .studentId(classRegistration.getStudent().getStudentId())
                .studentName(classRegistration.getStudent().getFullName())
                .classId(classRegistration.getAClass().getId())
                .classCode(classRegistration.getAClass().getClassCode())
                .createdDate(classRegistration.getCreatedAt())
                .updatedDate(classRegistration.getUpdatedAt())
                .createdBy(classRegistration.getCreatedBy())
                .updatedBy(classRegistration.getUpdatedBy())
                .build();
    }

    public static ClassRegistration mapFromClassRegistrationRequestToDomain(ClassRegistrationRequest classRegistrationRequest) {
        return ClassRegistration.builder()
                .status(classRegistrationRequest.getStatus())
                .build();
    }
}
