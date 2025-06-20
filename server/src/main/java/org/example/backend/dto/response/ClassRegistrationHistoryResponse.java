package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

@Getter
@Setter
@Builder
@Schema(description = "Response containing class registration history details")
public class ClassRegistrationHistoryResponse {

    @Schema(description = "ID of the history record", example = "1")
    private Integer id;

    @Schema(description = "Action taken on the registration", example = "REGISTERED")
    private RegistrationStatus action;

    @Schema(description = "Reason for the action", example = "Đăng ký học phần")
    private String reason;

    @Schema(description = "Class registration ID", example = "1")
    private Integer classRegistrationId;

    @Schema(description = "Class code associated with the registration", example = "CS101-01")
    private String classCode;

    @Schema(description = "Student ID associated with the registration", example = "SV001")
    private String studentId;

    @Schema(description = "User who created the registration", example = "admin")
    private String createdBy;

    @Schema(description = "User who last updated the registration", example = "admin")
     private String updatedBy;

    @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private String createdAt;

    @Schema(description = "Last updated timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private String updatedAt;
}
