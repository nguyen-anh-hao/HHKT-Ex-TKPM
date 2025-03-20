package org.example.backend.dto.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.domain.SinhVien;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class GiayToResponse {
    private String loaiGiayTo;
    private String soGiayTo;
    private LocalDate ngayCap;
    private LocalDate ngayHetHan;
    private String noiCap;
    private String quocGiaCap;
    private String ghiChu;
    private Boolean coGanChip;
}
