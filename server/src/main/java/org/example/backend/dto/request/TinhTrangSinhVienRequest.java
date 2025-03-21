package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TinhTrangSinhVienRequest {
    @NotBlank(message = "Tên tình trạng is required")
    private String tenTinhTrang;
}
