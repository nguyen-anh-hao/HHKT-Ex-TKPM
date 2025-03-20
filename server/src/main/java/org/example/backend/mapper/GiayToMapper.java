package org.example.backend.mapper;

import org.example.backend.domain.GiayTo;
import org.example.backend.dto.request.GiayToRequest;
import org.example.backend.dto.response.GiayToResponse;
import org.springframework.stereotype.Component;

@Component
public class GiayToMapper {
    public static GiayTo toDomain(GiayToRequest giayToRequest) {
        return GiayTo.builder()
                .loaiGiayTo(giayToRequest.getLoaiGiayTo())
                .soGiayTo(giayToRequest.getSoGiayTo())
                .ngayCap(giayToRequest.getNgayCap())
                .ngayHetHan(giayToRequest.getNgayHetHan())
                .noiCap(giayToRequest.getNoiCap())
                .quocGiaCap(giayToRequest.getQuocGiaCap())
                .ghiChu(giayToRequest.getGhiChu())
                .coGanChip(giayToRequest.getCoGanChip())
                .build();
    }

    public static GiayToResponse toResponseDTO(GiayTo giayTo) {
        return GiayToResponse.builder()
                .loaiGiayTo(giayTo.getLoaiGiayTo())
                .soGiayTo(giayTo.getSoGiayTo())
                .ngayCap(giayTo.getNgayCap())
                .ngayHetHan(giayTo.getNgayHetHan())
                .noiCap(giayTo.getNoiCap())
                .quocGiaCap(giayTo.getQuocGiaCap())
                .ghiChu(giayTo.getGhiChu())
                .coGanChip(giayTo.getCoGanChip())
                .build();
    }
}
