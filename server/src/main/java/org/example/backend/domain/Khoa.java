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
@Table(name = "khoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Khoa extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_khoa", unique = true, nullable = false)
    private String tenKhoa;

    @OneToMany(mappedBy = "khoa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SinhVien> sinhViens;
}
