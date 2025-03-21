package org.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.common.Auditable;

import java.util.List;

@Entity
@Table(name = "tinh_trang_sinh_vien")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TinhTrangSinhVien extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_tinh_trang", unique = true, nullable = false)
    private String tenTinhTrang;

    @OneToMany(mappedBy = "tinhTrang", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SinhVien> sinhViens;
}
