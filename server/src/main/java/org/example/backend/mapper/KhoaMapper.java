package org.example.backend.mapper;

import org.example.backend.domain.Khoa;
import org.example.backend.dto.request.KhoaRequest;
import org.example.backend.dto.response.KhoaResponse;

import java.util.stream.Collectors;

public class KhoaMapper {
    public static Khoa toDomainFromRequestDTO(KhoaRequest request) {
        return Khoa.builder()
                .tenKhoa(request.getTenKhoa())
                .build();
    }

    public static KhoaResponse toResponseDTO(Khoa khoa) {
        return KhoaResponse.builder()
                .id(khoa.getId())
                .tenKhoa(khoa.getTenKhoa())
                .sinhViens(khoa.getSinhViens() != null ? khoa.getSinhViens().stream().map(SinhVienMapper::toResponseDTO).collect(Collectors.toList()) : null)
                .createdAt(khoa.getCreatedAt())
                .updatedAt(khoa.getUpdatedAt())
                .createdBy(khoa.getCreatedBy())
                .updatedBy(khoa.getUpdatedBy())
                .build();
    }
}
