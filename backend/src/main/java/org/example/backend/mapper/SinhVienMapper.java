package org.example.backend.mapper;

import org.example.backend.domain.DiaChi;
import org.example.backend.domain.GiayTo;
import org.example.backend.domain.SinhVien;
import org.example.backend.dto.request.SinhVienRequest;
import org.example.backend.dto.response.SinhVienResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SinhVienMapper {

    public static SinhVien toDomainFromRequestDTO(SinhVienRequest sinhVienRequest) {
        SinhVien sinhVien = SinhVien.builder()
                .mssv(sinhVienRequest.getMssv())
                .hoTen(sinhVienRequest.getHoTen())
                .ngaySinh(sinhVienRequest.getNgaySinh())
                .gioiTinh(sinhVienRequest.getGioiTinh())
                .khoaHoc(sinhVienRequest.getKhoaHoc())
                .email(sinhVienRequest.getEmail())
                .soDienThoai(sinhVienRequest.getSoDienThoai())
                .quocTich(sinhVienRequest.getQuocTich())
                .build();

        if (sinhVienRequest.getDiaChis() != null) {
            sinhVien.setDiaChis(sinhVienRequest.getDiaChis().stream()
                    .map(diaChiRequest -> {
                        DiaChi diaChi = DiaChiMapper.toDomain(diaChiRequest);
                        diaChi.setSinhVien(sinhVien);
                        return diaChi;
                    })
                    .collect(Collectors.toList()));
        }

        if (sinhVienRequest.getGiayTos() != null) {
            sinhVien.setGiayTos(sinhVienRequest.getGiayTos().stream()
                    .map(giayToRequest -> {
                        GiayTo giayTo = GiayToMapper.toDomain(giayToRequest);
                        giayTo.setSinhVien(sinhVien);
                        return giayTo;
                    })
                    .collect(Collectors.toList()));
        }

        return sinhVien;
    }

    public static SinhVienResponse toResponseDTO(SinhVien sinhVien) {
        return SinhVienResponse.builder()
                .mssv(sinhVien.getMssv())
                .hoTen(sinhVien.getHoTen())
                .ngaySinh(sinhVien.getNgaySinh())
                .gioiTinh(sinhVien.getGioiTinh())
                .khoa(sinhVien.getKhoa().getTenKhoa())
                .khoaHoc(sinhVien.getKhoaHoc())
                .chuongTrinh(sinhVien.getChuongTrinh().getTenChuongTrinh())
                .email(sinhVien.getEmail())
                .soDienThoai(sinhVien.getSoDienThoai())
                .tinhTrang(sinhVien.getTinhTrang().getTenTinhTrang())
                .quocTich(sinhVien.getQuocTich())
                .diaChis(sinhVien.getDiaChis() != null ? sinhVien.getDiaChis().stream().map(DiaChiMapper::toResponeDTO).collect(Collectors.toList()) : null)
                .giayTos(sinhVien.getGiayTos() != null ? sinhVien.getGiayTos().stream().map(GiayToMapper::toResponseDTO).collect(Collectors.toList()) : null)
                .createdAt(sinhVien.getCreatedAt())
                .updatedAt(sinhVien.getUpdatedAt())
                .createdBy(sinhVien.getCreatedBy())
                .updatedBy(sinhVien.getUpdatedBy())
                .build();
    }
}
