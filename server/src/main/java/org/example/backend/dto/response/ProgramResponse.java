package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProgramResponse {

    @Schema(description = "Program ID", example = "1")
    private Integer id;

    @Schema(description = "Program name", example = "Đại học chính quy")
    private String programName;

    @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "User who created the program", example = "admin")
    private String createdBy;

    @Schema(description = "User who last updated the program", example = "admin")
    private String updatedBy;
}
