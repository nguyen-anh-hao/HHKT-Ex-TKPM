package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class StudentStatusResponse {

    @Schema(description = "Unique identifier for the student status", example = "1")
    private Integer id;

    @Schema(description = "Name of the student status", example = "Đang học")
    private String studentStatusName;

    @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last updated timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Created by user", example = "admin")
    private String createdBy;

    @Schema(description = "Last updated by user", example = "admin")
    private String updatedBy;
}
