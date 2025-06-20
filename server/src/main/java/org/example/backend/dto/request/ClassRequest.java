package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request body to create a new class")
public class ClassRequest {

    @Schema(description = "Unique code of the class", example = "CS101-01")
    @NotBlank(message = "Class code is required")
    private String classCode;

    @Schema(description = "Maximum number of students allowed", example = "50")
    @NotNull(message = "Max students is required")
    @Positive(message = "Max students must be greater than 0")
    private Integer maxStudents;

    @NotBlank(message = "Schedule is required")
    @Schema(description = "Schedule of the class", example = "Thứ Hai - Thứ Tư 10:00-12:00")
    private String schedule;

    @Schema(description = "Room where the class is held", example = "A101")
    @NotBlank(message = "Room is required")
    private String room;

    @Schema(description = "ID of the associated course", example = "1")
    @NotNull(message = "Course id is required")
    private Integer courseId;

    @Schema(description = "ID of the lecturer assigned", example = "1")
    @NotNull(message = "Lecturer id is required")
    private Integer lecturerId;

    @Schema(description = "ID of the semester this class belongs to", example = "1")
    @NotNull(message = "Semester id is required")
    private Integer semesterId;
}
