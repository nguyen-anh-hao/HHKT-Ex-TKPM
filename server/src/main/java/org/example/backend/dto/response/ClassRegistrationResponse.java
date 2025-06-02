package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ClassRegistrationResponse {
    private Integer id;
    private RegistrationStatus status;

    private String studentId;
    private String studentName;

    private Integer classId;
    private String classCode;

    private Double grade;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
