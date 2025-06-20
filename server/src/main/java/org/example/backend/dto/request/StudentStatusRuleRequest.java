package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentStatusRuleRequest {

    @NotNull(message = "Student status rule id is required")
    @Schema(description = "ID of the student status rule", example = "1")
    private Integer currentStatusId;

    @NotNull(message = "Student status rule id is required")
    @Schema(description = "ID of the allowed transition", example = "2")
    private Integer allowedTransitionId;
}
