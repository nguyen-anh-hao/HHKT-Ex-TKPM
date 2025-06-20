package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LecturerResponse {

    @Schema(description = "Lecturer ID", example = "1")
    private Integer id;

    @Schema(description = "Full name of the lecturer", example = "Nguyen Van A")
    private String fullName;

    @Schema(description = "Email address", example = "alice@example.com")
    private String email;

    @Schema(description = "Phone number", example = "+84987654321")
    private String phone;

    @Schema(description = "Faculty ID", example = "1")
    private Integer facultyId;

    @Schema(description = "Faculty name", example = "Công nghệ thông tin")
    private String facultyName;

    @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "User who created the lecturer", example = "admin")
    private String createdBy;

    @Schema(description = "User who last updated the lecturer", example = "admin")
    private String updatedBy;
}
