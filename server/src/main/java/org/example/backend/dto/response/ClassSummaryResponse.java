package org.example.backend.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassSummaryResponse {
    private String classCode;
    private Integer maxStudents;
    private String schedule;
    private String room;
}

