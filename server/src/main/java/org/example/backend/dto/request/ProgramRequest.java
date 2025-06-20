package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProgramRequest {

    @Schema(description = "Program name", example = "Đại học chính quy")
    @NotBlank(message = "Program name is required")
    private String programName;
}
