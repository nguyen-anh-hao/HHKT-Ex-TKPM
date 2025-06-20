package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FacultyRequest {

    @Schema(description = "Faculty name", example = "Khoa Công nghệ thông tin")
    @NotBlank(message = "Faculty name is required")
    private String facultyName;
}
