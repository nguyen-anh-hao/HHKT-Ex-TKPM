package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

import java.time.LocalDate;

@Entity
@Table(name = "giay_to")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiayTo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mssv")
    private SinhVien sinhVien;

    @Column(name = "loai_giay_to")
    private String loaiGiayTo;

    @Column(name = "so_giay_to")
    private String soGiayTo;

    @Column(name = "ngay_cap")
    private LocalDate ngayCap;

    @Column(name = "ngay_het_han")
    private LocalDate ngayHetHan;

    @Column(name = "noi_cap")
    private String noiCap;

    @Column(name = "quoc_gia_cap")
    private String quocGiaCap;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "co_gan_chip")
    private Boolean coGanChip = false;
}
