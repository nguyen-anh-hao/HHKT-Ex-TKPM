package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(description = "Response for class registration details")
public class ClassRegistrationResponse {

    @Schema(description = "Registration ID", example = "101")
    private Integer id;

    @Schema(description = "Registration status", example = "REGISTERED")
    private RegistrationStatus status;

    @Schema(description = "Student ID", example = "SV001")
    private String studentId;

    @Schema(description = "Student name", example = "Nguyen Van A")
    private String studentName;

    @Schema(description = "Class ID", example = "1")
    private Integer classId;

    @Schema(description = "Class code", example = "CS101-01")
    private String classCode;

    @Schema(description = "Student grade", example = "8.5")
    private Double grade;

    @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime createdDate;

    @Schema(description = "Last updated timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime updatedDate;

    @Schema(description = "User who created the registration", example = "admin")
    private String createdBy;

    @Schema(description = "User who last updated the registration", example = "admin")
    private String updatedBy;
}
