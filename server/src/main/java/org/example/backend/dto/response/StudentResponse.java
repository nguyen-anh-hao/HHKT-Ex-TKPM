package org.example.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class StudentResponse {

    private String studentId;
    private String fullName;
    private LocalDate dob;
    private String gender;
    private String faculty;
    private String intake;
    private String program;
    private String email;
    private String phoneCountry;
    private String phone;
    private String studentStatus;
    private String nationality;
    private List<AddressResponse> addresses;
    private List<DocumentResponse> documents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
