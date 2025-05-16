package org.example.backend.mapper;

import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.dto.response.StudentStatusResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentStatusMapper {
    public static StudentStatus mapToDomain(StudentStatusRequest request) {
        return StudentStatus.builder()
                .studentStatusName(request.getStudentStatusName())
                .build();
    }

    public static StudentStatusResponse mapToResponse(StudentStatus studentStatus) {
        return StudentStatusResponse.builder()
                .id(studentStatus.getId())
                .studentStatusName(studentStatus.getStudentStatusName())
                .students(studentStatus.getStudents() != null ? studentStatus.getStudents().stream().map(StudentMapper::mapToResponse).collect(Collectors.toList()) : null)
                .createdAt(studentStatus.getCreatedAt())
                .updatedAt(studentStatus.getUpdatedAt())
                .createdBy(studentStatus.getCreatedBy())
                .updatedBy(studentStatus.getUpdatedBy())
                .build();
    }

    public static StudentStatusResponse mapToResponseWithTranslation(
            StudentStatus studentStatus,
            Map<String, String> statusTranslations,
            Map<Integer, Map<String, String>> studentTranslations) {

        List<StudentResponse> studentResponses = null;
        if (studentStatus.getStudents() != null) {
            studentResponses = studentStatus.getStudents().stream()
                    .map(student -> {
                        Map<String, String> studentTrans = studentTranslations.getOrDefault(Integer.parseInt(student.getStudentId().replaceAll("[^\\d]", "")), Collections.emptyMap());

                        // Pass empty maps for other translation types since the full method requires them
                        return StudentMapper.mapToResponseWithTranslation(
                                student,
                                studentTrans,
                                Collections.emptyMap(), // facultyTranslations
                                Collections.emptyMap(), // programTranslations
                                Collections.emptyMap(), // statusTranslations
                                Collections.emptyMap(), // addressTranslations
                                Collections.emptyMap()  // documentTranslations
                        );
                    })
                    .collect(Collectors.toList());
        }

        return StudentStatusResponse.builder()
                .id(studentStatus.getId())
                .studentStatusName(statusTranslations.getOrDefault("studentStatusName", studentStatus.getStudentStatusName()))
                .students(studentResponses)
                .createdAt(studentStatus.getCreatedAt())
                .updatedAt(studentStatus.getUpdatedAt())
                .createdBy(studentStatus.getCreatedBy())
                .updatedBy(studentStatus.getUpdatedBy())
                .build();
    }
}
