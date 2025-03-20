package org.example.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaChiRequest {
    private String loaiDiaChi;
    private String soNhaTenDuong;
    private String phuongXa;
    private String quanHuyen;
    private String tinhThanhPho;
    private String quocGia;
}
