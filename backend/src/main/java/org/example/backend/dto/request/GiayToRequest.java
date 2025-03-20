package org.example.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GiayToRequest {
    private String loaiGiayTo;
    private String soGiayTo;
    private LocalDate ngayCap;
    private LocalDate ngayHetHan;
    private String noiCap;
    private String quocGiaCap;
    private String ghiChu;
    private Boolean coGanChip;
}
