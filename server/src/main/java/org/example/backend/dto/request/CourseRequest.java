package org.example.backend.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotBlank(message = "Course name is required")
    private String courseName;

    @NotNull(message = "Credits is required")
    @Min(value = 2, message = "Credits must be greater than 2")
    private Integer credits;

    // optional
    private String description;

    // default value is true
    private Boolean isActive = true;

    @NotNull(message = "Faculty id is required")
    private Integer facultyId;

    // optional
    private Integer prerequisiteCourseId;
}
