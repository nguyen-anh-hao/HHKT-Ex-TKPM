package org.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.common.Auditable;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sinh_vien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SinhVien extends Auditable {
    @Id
    @Column(name = "mssv", length = 10)
    private String mssv;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "gioi_tinh", nullable = false)
    private String gioiTinh;

    @ManyToOne
    @JoinColumn(name = "khoa_id")
    private Khoa khoa;

    @Column(name = "khoa_hoc")
    private String khoaHoc;

    @ManyToOne
    @JoinColumn(name = "chuong_trinh_id")
    private ChuongTrinh chuongTrinh;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "so_dien_thoai", nullable = false, unique = true)
    private String soDienThoai;

    @ManyToOne
    @JoinColumn(name = "tinh_trang_id")
    private TinhTrangSinhVien tinhTrang;

    @Column(name = "quoc_tich")
    private String quocTich;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaChi> diaChis;

    @OneToMany(mappedBy= "sinhVien", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<GiayTo> giayTos;
}
