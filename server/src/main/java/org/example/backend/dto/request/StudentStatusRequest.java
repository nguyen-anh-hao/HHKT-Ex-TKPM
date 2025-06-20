package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentStatusRequest {

    @NotBlank(message = "Student status name is required")
    @Schema(description = "Name of the student status", example = "Đang học")
    private String studentStatusName;
}
