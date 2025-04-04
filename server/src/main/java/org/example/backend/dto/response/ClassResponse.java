package org.example.backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ClassResponse {
    private Integer id;
    private String classCode;
    private Integer maxStudents;
    private String schedule;
    private String room;

    private Integer courseId;
    private String courseCode;
    private String courseName;
    private Integer credits;
    private String facultyName;
    private String prerequisiteCourseCode;
    private String prerequisiteCourseName;

    private Integer lecturerId;
    private String lecturerName;

    private Integer semesterId;
    private Integer semesterName;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
