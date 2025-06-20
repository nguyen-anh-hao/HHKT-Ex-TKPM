package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

@Getter
@Setter
@Schema(description = "Request to register a student to a class")
public class ClassRegistrationRequest {

    @Schema(description = "Registration status", defaultValue = "REGISTERED", example = "REGISTERED")
    private RegistrationStatus status = RegistrationStatus.REGISTERED;

    @NotBlank(message = "Student ID is required")
    @Schema(description = "Student ID", example = "SV001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String studentId;

    @NotNull(message = "Class ID is required")
    @Schema(description = "Class ID to register", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer classId;
}
