package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request body for creating a class registration history")
public class ClassRegistrationHistoryRequest {

    @Schema(description = "Action performed on the class registration", example = "REGISTERED", required = true)
    @NotBlank(message = "Action is required")
    private RegistrationStatus action;

    @Schema(description = "Reason for the registration action", example = "Đăng ký học phần")
    private String reason;

    @NotNull(message = "Class Registration ID is required")
    @Schema(description = "ID of the class registration", example = "1")
    private Integer classRegistrationId;

}
