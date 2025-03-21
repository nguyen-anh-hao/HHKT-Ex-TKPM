package org.example.backend.mapper;

import org.example.backend.domain.ChuongTrinh;
import org.example.backend.dto.request.ChuongTrinhRequest;
import org.example.backend.dto.response.ChuongTrinhResponse;

import java.util.stream.Collectors;

public class ChuongTrinhMapper {
    public static ChuongTrinh toDomainFromRequestDTO(ChuongTrinhRequest request) {
        return ChuongTrinh.builder()
                .tenChuongTrinh(request.getTenChuongTrinh())
                .build();
    }

    public static ChuongTrinhResponse toResponseDTO(ChuongTrinh chuongTrinh) {
        return ChuongTrinhResponse.builder()
                .id(chuongTrinh.getId())
                .tenChuongTrinh(chuongTrinh.getTenChuongTrinh())
                .sinhViens(chuongTrinh.getSinhViens() != null ? chuongTrinh.getSinhViens().stream().map(SinhVienMapper::toResponseDTO).collect(Collectors.toList()) : null)
                .createdAt(chuongTrinh.getCreatedAt())
                .updatedAt(chuongTrinh.getUpdatedAt())
                .createdBy(chuongTrinh.getCreatedBy())
                .updatedBy(chuongTrinh.getUpdatedBy())
                .build();
    }
}
