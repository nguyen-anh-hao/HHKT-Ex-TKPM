package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CourseResponse {
        private Integer courseId;
        private String courseCode;
        private String courseName;
        private Integer credits;
        private String description;
        private Boolean isActive;

        private Integer facultyId;
        private String facultyName;

        private Integer prerequisiteCourseId;
        private String prerequisiteCourseName;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
}
