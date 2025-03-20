package org.example.backend.mapper;

import org.example.backend.domain.DiaChi;
import org.example.backend.dto.request.DiaChiRequest;
import org.example.backend.dto.response.DiaChiResponse;
import org.springframework.stereotype.Component;

@Component
public class DiaChiMapper {
    public static DiaChi toDomain(DiaChiRequest diaChiRequest) {
        return DiaChi.builder()
                .loaiDiaChi(diaChiRequest.getLoaiDiaChi())
                .soNhaTenDuong(diaChiRequest.getSoNhaTenDuong())
                .phuongXa(diaChiRequest.getPhuongXa())
                .quanHuyen(diaChiRequest.getQuanHuyen())
                .tinhThanhPho(diaChiRequest.getTinhThanhPho())
                .quocGia(diaChiRequest.getQuocGia())
                .build();
    }

    public static DiaChiResponse toResponeDTO(DiaChi diaChi) {
        return DiaChiResponse.builder()
                .loaiDiaChi(diaChi.getLoaiDiaChi())
                .soNhaTenDuong(diaChi.getSoNhaTenDuong())
                .phuongXa(diaChi.getPhuongXa())
                .quanHuyen(diaChi.getQuanHuyen())
                .tinhThanhPho(diaChi.getTinhThanhPho())
                .quocGia(diaChi.getQuocGia())
                .build();
    }
}
