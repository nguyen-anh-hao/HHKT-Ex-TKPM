package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProgramRequest {
    @NotBlank(message = "Program name is required")
    private String programName;
}
