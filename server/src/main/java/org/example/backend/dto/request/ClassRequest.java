package org.example.backend.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.Course;
import org.example.backend.domain.Lecturer;
import org.example.backend.domain.Semester;

import java.util.List;

@Getter
@Setter
public class ClassRequest {

    @NotBlank(message = "Class code is required")
    private String classCode;

    @NotNull(message = "Max students is required")
    @Positive(message = "Max students must be greater than 0")
    private Integer maxStudents;

    @NotBlank(message = "Schedule is required")
    private String schedule;

    @NotBlank(message = "Room is required")
    private String room;

    @NotNull(message = "Course id is required")
    private Integer courseId;

    @NotNull(message = "Lecturer id is required")
    private Integer lecturerId;

    @NotNull(message = "Semester id is required")
    private Integer semesterId;
}
