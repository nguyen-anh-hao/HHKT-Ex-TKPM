package org.example.backend.mapper;

import org.example.backend.domain.Faculty;
import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.FacultyResponse;

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
}
