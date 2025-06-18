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
                .createdAt(studentStatus.getCreatedAt())
                .updatedAt(studentStatus.getUpdatedAt())
                .createdBy(studentStatus.getCreatedBy())
                .updatedBy(studentStatus.getUpdatedBy())
                .build();
    }
}
