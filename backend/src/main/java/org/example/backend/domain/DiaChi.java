package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

@Entity
@Table(name = "dia_chi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaChi extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mssv")
    private SinhVien sinhVien;

    @Column(name = "loai_dia_chi")
    private String loaiDiaChi;

    @Column(name = "so_nha_ten_duong")
    private String soNhaTenDuong;

    @Column(name = "phuong_xa")
    private String phuongXa;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "tinh_thanh_pho")
    private String tinhThanhPho;

    @Column(name = "quoc_gia")
    private String quocGia;
}
