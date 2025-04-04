package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClassRegistrationHistoryResponse {
    private Integer id;
    private String action;
    private String reason;
    private Integer classRegistrationId;
    private String classCode;
    private String studentId;

    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedAt;
}
