package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Schema(description = "Detailed class response with course, lecturer, and semester information")
public class ClassResponse {
    @Schema(description = "Unique ID of the class", example = "1")
    private Integer id;

    @Schema(description = "Class code", example = "CS101-01")
    private String classCode;

    @Schema(description = "Maximum number of students", example = "50")
    private Integer maxStudents;

    @Schema(description = "Class schedule", example = "Thứ Hai - Thứ Tư 10:00-12:00")
    private String schedule;

    @Schema(description = "Room name or number", example = "A101")
    private String room;

    @Schema(description = "Course ID", example = "1")
    private Integer courseId;

    @Schema(description = "Course code", example = "CS101")
    private String courseCode;

    @Schema(description = "Course name", example = "Nhập môn Lập trình")
    private String courseName;

    @Schema(description = "Credits for the course", example = "3")
    private Integer credits;

    @Schema(description = "Faculty name", example = "Công nghệ Thông tin")
    private String facultyName;

    @Schema(description = "Prerequisite course code", example = "CS100")
    private String prerequisiteCourseCode;

    @Schema(description = "Prerequisite course name", example = "Lập trình Cơ bản")
    private String prerequisiteCourseName;

    @Schema(description = "Lecturer ID", example = "2")
    private Integer lecturerId;

    @Schema(description = "Lecturer full name", example = "Nguyễn Văn A")
    private String lecturerName;

    @Schema(description = "Semester ID", example = "1")
    private Integer semesterId;

    @Schema(description = "Semester name", example = "2024")
    private Integer semesterName;

    @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime createdDate;

    @Schema(description = "Last update timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime updatedDate;

    @Schema(description = "User who created the class", example = "admin")
    private String createdBy;

    @Schema(description = "User who last updated the class", example = "admin")
    private String updatedBy;
}
