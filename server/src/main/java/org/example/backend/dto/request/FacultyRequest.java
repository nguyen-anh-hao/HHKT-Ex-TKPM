package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacultyRequest {
    @NotBlank(message = "Faculty name is required")
    private String facultyName;
}
