package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClassRegistrationHistoryRequest {
    @NotBlank(message = "Action is required")
    @Pattern(regexp = "^(REGISTERED|CANCELLED)$", message = "Action must be either REGISTERED or CANCELLED")
    private String action;

    private String reason;

    @NotNull(message = "Class Registration ID is required")
    private Integer classRegistrationId;

}
