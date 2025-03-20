package org.example.backend.dto.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class SinhVienResponse {

    private String mssv;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String khoa;
    private String khoaHoc;
    private String chuongTrinh;
    private String email;
    private String soDienThoai;
    private String tinhTrang;
    private String quocTich;
    private List<DiaChiResponse> diaChis;
    private List<GiayToResponse> giayTos;
    // Auditing fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
