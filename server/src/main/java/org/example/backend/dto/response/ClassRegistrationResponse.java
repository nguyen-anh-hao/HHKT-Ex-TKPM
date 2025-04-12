package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ClassRegistrationResponse {
    private Integer id;
    private String status;
    private LocalDateTime registrationDate;
    private LocalDateTime cancellationDate;

    private String studentId;
    private String studentName;

    private Integer classId;
    private String classCode;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
