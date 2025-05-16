package org.example.backend.mapper;

import org.example.backend.domain.Faculty;
import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.FacultyResponse;
import org.example.backend.dto.response.StudentResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FacultyMapper {
    public static Faculty mapToDomain(FacultyRequest request) {
        return Faculty.builder()
                .facultyName(request.getFacultyName())
                .build();
    }

    public static FacultyResponse mapToResponse(Faculty faculty) {
        return FacultyResponse.builder()
                .id(faculty.getId())
                .facultyName(faculty.getFacultyName())
                .students(faculty.getStudents() != null ? faculty.getStudents().stream().map(StudentMapper::mapToResponse).collect(Collectors.toList()) : null)
                .createdAt(faculty.getCreatedAt())
                .updatedAt(faculty.getUpdatedAt())
                .createdBy(faculty.getCreatedBy())
                .updatedBy(faculty.getUpdatedBy())
                .build();
    }

    public static FacultyResponse mapToResponseWithTranslation(
            Faculty faculty,
            Map<String, String> facultyTranslations,
            Map<Integer, Map<String, String>> studentTranslations) {

        List<StudentResponse> studentResponses = null;
        if (faculty.getStudents() != null) {
            studentResponses = faculty.getStudents().stream()
                    .map(student -> {
                        Map<String, String> studentTrans = studentTranslations.getOrDefault(Integer.parseInt(student.getStudentId().replaceAll("[^\\d]", "")), Collections.emptyMap());

                        return StudentMapper.mapToResponseWithTranslation(
                                student,
                                studentTrans,
                                Collections.emptyMap(),
                                Collections.emptyMap(),
                                Collections.emptyMap(),
                                Collections.emptyMap(),
                                Collections.emptyMap()
                        );
                    })
                    .collect(Collectors.toList());
        }

        return FacultyResponse.builder()
                .id(faculty.getId())
                .facultyName(facultyTranslations.getOrDefault("facultyName", faculty.getFacultyName()))
                .students(studentResponses)
                .createdAt(faculty.getCreatedAt())
                .updatedAt(faculty.getUpdatedAt())
                .createdBy(faculty.getCreatedBy())
                .updatedBy(faculty.getUpdatedBy())
                .build();
    }
}
