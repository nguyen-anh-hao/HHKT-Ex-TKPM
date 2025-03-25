package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentStatusRequest {
    @NotBlank(message = "Student status name is required")
    private String studentStatusName;
}
