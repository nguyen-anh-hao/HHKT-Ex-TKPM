package org.example.backend.mapper;

import org.example.backend.domain.Lecturer;
import org.example.backend.dto.request.LecturerRequest;
import org.example.backend.dto.response.ClassSummaryResponse;
import org.example.backend.dto.response.LecturerResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public static LecturerResponse mapFromDomainToResponseWithTranslation(
            Lecturer lecturer,
            Map<String, String> facultyTranslations,
            Map<Integer, Map<String, String>> classTranslations) {

        List<ClassSummaryResponse> classResponses = null;
        if (lecturer.getClasses() != null && !lecturer.getClasses().isEmpty()) {
            classResponses = lecturer.getClasses().stream()
                    .map(aClass -> {
                        Map<String, String> translations = classTranslations.getOrDefault(aClass.getId(), Collections.emptyMap());
                        return ClassMapper.mapFromDomainToClassSummaryResponseWithTranslation(aClass, translations);
                    })
                    .toList();
        }

        return LecturerResponse.builder()
                .id(lecturer.getId())
                .fullName(lecturer.getFullName())
                .email(lecturer.getEmail())
                .phone(lecturer.getPhone())
                .facultyId(lecturer.getFaculty().getId())
                .facultyName(facultyTranslations.getOrDefault("facultyName", lecturer.getFaculty().getFacultyName()))
                .classes(classResponses)
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
