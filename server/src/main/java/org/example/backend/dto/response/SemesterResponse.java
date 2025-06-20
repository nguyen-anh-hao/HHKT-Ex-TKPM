package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SemesterResponse {

        @Schema(description = "Unique semester ID", example = "1")
        private Integer id;

        @Schema(description = "Semester number", example = "1")
        private Integer semester;

        @Schema(description = "Start date of the semester", example = "2024-09-01")
        private LocalDate startDate;

        @Schema(description = "End date of the semester", example = "2025-01-31")
        private LocalDate endDate;

        @Schema(description = "Academic year in format YYYY-YYYY", example = "2024-2025")
        private String academicYear;

        @Schema(description = "Last cancel date for the semester", example = "2024-12-15")
        private LocalDate lastCancelDate;

        @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
        private LocalDateTime createdAt;

        @Schema(description = "Last update timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
        private LocalDateTime updatedAt;

        @Schema(description = "User who created the semester", example = "admin")
        private String createdBy;

        @Schema(description = "User who last updated the semester", example = "admin")
        private String updatedBy;
}
