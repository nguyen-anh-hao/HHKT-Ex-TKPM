package org.example.backend.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

@Getter
@Setter
public class ClassRegistrationUpdateRequest {
    @NotNull
    private RegistrationStatus status;

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
