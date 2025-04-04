package org.example.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseUpdateRequest {

        private String courseName;

        private Integer credits;

        private String description;

        private Integer facultyId;

}
