package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Summary information of a class")
public class ClassSummaryResponse {

    @Schema(description = "Class code", example = "CS101-01")
    private String classCode;

    @Schema(description = "Maximum number of students", example = "50")
    private Integer maxStudents;

    @Schema(description = "Schedule of the class", example = "Thứ Hai - Thứ Tư 10:00-12:00")
    private String schedule;

    @Schema(description = "Room of the class", example = "A101")
    private String room;
}

