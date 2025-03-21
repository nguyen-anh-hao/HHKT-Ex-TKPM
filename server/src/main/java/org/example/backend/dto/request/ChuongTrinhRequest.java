package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChuongTrinhRequest {
    @NotBlank(message = "Tên chương trình is required")
    private String tenChuongTrinh;
}
