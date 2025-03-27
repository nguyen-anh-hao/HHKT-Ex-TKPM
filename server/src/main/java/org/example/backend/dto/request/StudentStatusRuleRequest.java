package org.example.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentStatusRuleRequest {

    @NotNull(message = "Student status rule id is required")
    private Integer currentStatusId;

    @NotNull(message = "Student status rule id is required")
    private Integer allowedTransitionId;
}
