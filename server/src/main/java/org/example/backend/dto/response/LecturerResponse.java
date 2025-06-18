package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LecturerResponse {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private Integer facultyId;
    private String facultyName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
