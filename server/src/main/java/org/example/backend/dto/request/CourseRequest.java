package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

    @Schema(description = "Course code", example = "CS101", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Course code is required")
    private String courseCode;

    @Schema(description = "Course name", example = "Nhập môn Lập trình", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Course name is required")
    private String courseName;

    @Schema(description = "Number of credits", example = "3", minimum = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Credits is required")
    @Min(value = 2, message = "Credits must be greater than 2")
    private Integer credits;

    // optional
    @Schema(description = "Course description", example = "Các khái niệm lập trình cơ bản")
    private String description;

    // default value is true
    @Schema(description = "Whether the course is active", example = "true", defaultValue = "true")
    private Boolean isActive = true;

    @Schema(description = "ID of the faculty this course belongs to", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Faculty id is required")
    private Integer facultyId;

    // optional
    @Schema(description = "ID of the prerequisite course", example = "1")
    private Integer prerequisiteCourseId;
}
