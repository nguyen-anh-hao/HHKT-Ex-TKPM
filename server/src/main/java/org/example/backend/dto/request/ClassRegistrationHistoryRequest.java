package org.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

@Getter
@Setter
@Builder
public class ClassRegistrationHistoryRequest {
    @NotBlank(message = "Action is required")
    private RegistrationStatus action;

    private String reason;

    @NotNull(message = "Class Registration ID is required")
    private Integer classRegistrationId;

}
