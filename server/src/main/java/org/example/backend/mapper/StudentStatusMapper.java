package org.example.backend.mapper;

import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentStatusResponse;

import java.util.stream.Collectors;

public class StudentStatusMapper {
    public static StudentStatus toDomainFromRequestDTO(StudentStatusRequest request) {
        return StudentStatus.builder()
                .studentStatusName(request.getStudentStatusName())
                .build();
    }

    public static StudentStatusResponse toResponseDTO(StudentStatus tinhTrangSinhVien) {
        return StudentStatusResponse.builder()
                .id(tinhTrangSinhVien.getId())
                .studentStatusName(tinhTrangSinhVien.getStudentStatusName())
                .students(tinhTrangSinhVien.getStudents() != null ? tinhTrangSinhVien.getStudents().stream().map(StudentMapper::mapToResponse).collect(Collectors.toList()) : null)
                .createdAt(tinhTrangSinhVien.getCreatedAt())
                .updatedAt(tinhTrangSinhVien.getUpdatedAt())
                .createdBy(tinhTrangSinhVien.getCreatedBy())
                .updatedBy(tinhTrangSinhVien.getUpdatedBy())
                .build();
    }
}
