package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramRequest {
    @NotBlank(message = "Program name is required")
    private String programName;
}
