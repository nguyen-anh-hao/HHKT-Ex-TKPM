package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CourseResponse {

        @Schema(description = "Unique ID of the course", example = "1")
        private Integer courseId;

        @Schema(description = "Course code", example = "CS101")
        private String courseCode;

        @Schema(description = "Course name", example = "Nhập môn Lập trình")
        private String courseName;

        @Schema(description = "Number of credits", example = "3")
        private Integer credits;

        @Schema(description = "Course description", example = "Các khái niệm lập trình cơ bản")
        private String description;

        @Schema(description = "Whether the course is active", example = "true")
        private Boolean isActive;

        @Schema(description = "Faculty ID this course belongs to", example = "1")
        private Integer facultyId;

        @Schema(description = "Name of the faculty", example = "Công nghệ thông tin")
        private String facultyName;

        @Schema(description = "Prerequisite course ID", example = "1")
        private Integer prerequisiteCourseId;

        @Schema(description = "Name of the prerequisite course", example = "Nhập môn Lập trình")
        private String prerequisiteCourseName;

        @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
        private LocalDateTime createdAt;

        @Schema(description = "Last update timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
        private LocalDateTime updatedAt;

        @Schema(description = "User who created the course", example = "admin")
        private String createdBy;

        @Schema(description = "User who last updated the course", example = "admin")
        private String updatedBy;
}
