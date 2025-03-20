package org.example.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SinhVienRequest {
    @NotBlank(message = "MSSV is required")
    private String mssv;

    @NotBlank(message = "Họ tên is required")
    private String hoTen;

    @NotBlank(message = "Ngày sinh is required")
    private LocalDate ngaySinh;

    @NotBlank(message = "Giới tính is required")
    private String gioiTinh;

    @NotNull(message = "Khoa id is required")
    private Integer khoaId;

    @NotBlank(message = "Khóa học is required")
    private String khoaHoc;

    @NotNull(message = "Chương trình id is required")
    private Integer chuongTrinhId;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Số điện thoại is required")
    @Pattern(regexp = "^(0[0-9]{9})$", message = "Số điện thoại không hợp lệ")
    private String soDienThoai;

    @NotNull(message = "Tình trạng id is required")
    private Integer tinhTrangId;

    @NotBlank(message = "Quốc tịch is required")
    private String quocTich;

    private List<DiaChiRequest> diaChis;

    private List<GiayToRequest> giayTos;
}
