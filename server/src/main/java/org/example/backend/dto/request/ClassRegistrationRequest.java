package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

@Getter
@Setter
public class ClassRegistrationRequest {
    private RegistrationStatus status = RegistrationStatus.REGISTERED;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotNull(message = "Class ID is required")
    private Integer classId;
}
