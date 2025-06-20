package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseUpdateRequest {

        @Schema(description = "Updated course name", example = "Cấu trúc Dữ liệu nâng cao")
        private String courseName;

        @Schema(description = "Updated number of credits", example = "4")
        private Integer credits;

        @Schema(description = "Updated course description", example = "Khóa học nâng cao về cấu trúc dữ liệu")
        private String description;

        @Schema(description = "Updated faculty ID", example = "2")
        private Integer facultyId;

}
