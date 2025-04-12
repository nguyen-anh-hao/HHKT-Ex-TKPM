package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.common.RegistrationStatus;

@Getter
@Setter
@Builder
public class ClassRegistrationHistoryResponse {
    private Integer id;
    private RegistrationStatus action;
    private String reason;

    private Integer classRegistrationId;

    private String classCode;
    private String studentId;

    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedAt;
}
