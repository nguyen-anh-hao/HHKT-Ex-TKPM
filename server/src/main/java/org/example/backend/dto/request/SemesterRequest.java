package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SemesterRequest {

    @Schema(description = "Unique semester ID", example = "1")
    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be between 1 and 3")
    @Max(value = 3, message = "Semester must be between 1 and 3")
    private Integer semester;

    @Schema(description = "Start date of the semester", example = "2024-09-01")
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Schema(description = "End date of the semester", example = "2025-01-31")
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Schema(description = "Academic year in format YYYY-YYYY", example = "2024-2025")
    @NotBlank(message = "Academic year is required")
    @Pattern(regexp = "^\\d{4}-\\d{4}$", message = "Academic year must be in format YYYY-YYYY")
    private String academicYear;

    @Schema(description = "Last cancel date for the semester", example = "2024-12-15")
    @NotNull(message = "Last cancel date is required")
    private LocalDate lastCancelDate;

    @AssertTrue(message = "Start data must before last cancel date and last cancel date must be before end date")
    public boolean isValidDates() {
        return startDate != null && endDate != null && lastCancelDate != null
                && startDate.isBefore(lastCancelDate)
                && lastCancelDate.isBefore(endDate);
    }

    @AssertTrue(message = "Start date and end date must be in the same academic year")
    public boolean isValidAcademicYear() {
        if (startDate == null || endDate == null) {
            return false;
        }

        String[] years = academicYear.split("-");
        int startYear = Integer.parseInt(years[0]);
        int endYear = Integer.parseInt(years[1]);

        return (startDate.getYear() == startYear || startDate.getYear() == endYear)
                && (endDate.getYear() == startYear || endDate.getYear() == endYear)
                && startYear <= endYear
                && startDate.getYear() <= endDate.getYear();
    }
}
