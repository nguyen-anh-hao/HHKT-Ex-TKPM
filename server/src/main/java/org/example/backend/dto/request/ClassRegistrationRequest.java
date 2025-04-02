package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
public class ClassRegistrationRequest {

    @Pattern(regexp = "^(REGISTERED|CANCELLED)$", message = "Status must be either REGISTERED or CANCELLED")
    private String status = "REGISTERED";

    private LocalDateTime registrationDate = LocalDateTime.now();

    private LocalDateTime cancellationDate;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotNull(message = "Class ID is required")
    private Integer classId;
}
