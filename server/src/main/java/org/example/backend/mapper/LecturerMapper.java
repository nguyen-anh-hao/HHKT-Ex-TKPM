package org.example.backend.mapper;

import org.example.backend.domain.Lecturer;
import org.example.backend.dto.request.LecturerRequest;
import org.example.backend.dto.response.LecturerResponse;
import org.springframework.stereotype.Component;

@Component
public class LecturerMapper {
    public static LecturerResponse mapFromDomainToResponse(Lecturer lecturer) {
        return LecturerResponse.builder()
                .id(lecturer.getId())
                .fullName(lecturer.getFullName())
                .email(lecturer.getEmail())
                .phone(lecturer.getPhone())
                .facultyId(lecturer.getFaculty().getId())
                .facultyName(lecturer.getFaculty().getFacultyName())
                .classes(lecturer.getClasses() != null ? lecturer.getClasses().stream().map(ClassMapper::mapFromDomainToClassSummaryResponse).toList() : null)
                .createdAt(lecturer.getCreatedAt())
                .updatedAt(lecturer.getUpdatedAt())
                .createdBy(lecturer.getCreatedBy())
                .updatedBy(lecturer.getUpdatedBy())
                .build();
    }

    public static Lecturer mapFromRequestToDomain(LecturerRequest request) {
        return Lecturer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }
}
