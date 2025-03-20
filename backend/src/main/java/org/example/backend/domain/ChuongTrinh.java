package org.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.Auditable;

import java.util.List;

@Entity
@Table(name = "chuong_trinh")
@Getter
@Setter
public class ChuongTrinh extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_chuong_trinh", nullable = false)
    private String tenChuongTrinh;

    @OneToMany(mappedBy = "chuongTrinh", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SinhVien> sinhViens;
}
