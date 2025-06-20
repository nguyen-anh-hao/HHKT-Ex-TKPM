package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

@Getter
@Setter
@Schema(description = "Request to update registration status or grade of a class registration")
public class ClassRegistrationUpdateRequest {

    @NotNull
    @Schema(description = "Updated registration status", example = "CANCELLED", requiredMode = Schema.RequiredMode.REQUIRED)
    private RegistrationStatus status;

    @Schema(description = "Student grade (must be a multiple of 0.5 and between 0 and 10)", example = "8.5")
    private Double grade;

    @AssertTrue(message = "Grade must be a multiple of 0.5")
    public boolean isGradeValid() {
        return grade == null || (grade * 10) % 5 == 0;
    }

    @AssertTrue(message = "Grade must be between 0 and 10")
    public boolean isGradeInRange() {
        return grade == null || (grade >= 0 && grade <= 10);
    }
}
