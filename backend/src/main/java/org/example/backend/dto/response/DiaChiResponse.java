package org.example.backend.dto.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.domain.SinhVien;

@Getter
@Setter
@Builder
public class DiaChiResponse {
    private String loaiDiaChi;
    private String soNhaTenDuong;
    private String phuongXa;
    private String quanHuyen;
    private String tinhThanhPho;
    private String quocGia;
}
