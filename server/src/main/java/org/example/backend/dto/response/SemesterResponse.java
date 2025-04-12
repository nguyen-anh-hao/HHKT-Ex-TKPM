package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SemesterResponse {
        private Integer id;
        private Integer semester;
        private LocalDate startDate;
        private LocalDate endDate;
        private String academicYear;
        private LocalDate lastCancelDate;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
}
