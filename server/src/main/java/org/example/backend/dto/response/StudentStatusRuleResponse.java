package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StudentStatusRuleResponse {

    @Schema(description = "ID of the student status rule", example = "1")
    private Integer id;

    @Schema(description = "Name of the current status", example = "Đang học")
    private String currentStatusName;

    @Schema(description = "Name of the allowed transition", example = "Đã tốt nghiệp")
    private String allowedTransitionName;

    @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last updated timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "User who created this rule", example = "admin")
    private String createdBy;

    @Schema(description = "User who last updated this rule", example = "admin")
    private String updatedBy;
}
