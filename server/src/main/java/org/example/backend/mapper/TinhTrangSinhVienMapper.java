package org.example.backend.mapper;

import org.example.backend.domain.TinhTrangSinhVien;
import org.example.backend.dto.request.TinhTrangSinhVienRequest;
import org.example.backend.dto.response.TinhTrangSinhVienResponse;

import java.util.stream.Collectors;

public class TinhTrangSinhVienMapper {
    public static TinhTrangSinhVien toDomainFromRequestDTO(TinhTrangSinhVienRequest request) {
        return TinhTrangSinhVien.builder()
                .tenTinhTrang(request.getTenTinhTrang())
                .build();
    }

    public static TinhTrangSinhVienResponse toResponseDTO(TinhTrangSinhVien tinhTrangSinhVien) {
        return TinhTrangSinhVienResponse.builder()
                .id(tinhTrangSinhVien.getId())
                .tenTinhTrang(tinhTrangSinhVien.getTenTinhTrang())
                .sinhViens(tinhTrangSinhVien.getSinhViens() != null ? tinhTrangSinhVien.getSinhViens().stream().map(SinhVienMapper::toResponseDTO).collect(Collectors.toList()) : null)
                .createdAt(tinhTrangSinhVien.getCreatedAt())
                .updatedAt(tinhTrangSinhVien.getUpdatedAt())
                .createdBy(tinhTrangSinhVien.getCreatedBy())
                .updatedBy(tinhTrangSinhVien.getUpdatedBy())
                .build();
    }
}
