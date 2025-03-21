package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KhoaRequest {
    @NotBlank(message = "Tên khoa is required")
    private String tenKhoa;
}
