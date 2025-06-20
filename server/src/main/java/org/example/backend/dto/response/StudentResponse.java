package org.example.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Unique student ID", example = "SV001")
    private String studentId;

    @Schema(description = "Full name", example = "Alice Nguyen")
    private String fullName;

    @Schema(description = "Date of birth", example = "2003-04-15")
    private LocalDate dob;

    @Schema(description = "Gender", example = "Nam")
    private String gender;

    @Schema(description = "Faculty name", example = "Khoa Công nghệ thông tin")
    private String faculty;

    @Schema(description = "Intake batch or semester", example = "K20")
    private String intake;

    @Schema(description = "Program name", example = "Công nghệ phần mềm")
    private String program;

    @Schema(description = "Email address", example = "alice@example.com")
    private String email;

    @Schema(description = "Phone country code", example = "VN")
    private String phoneCountry;

    @Schema(description = "Phone number", example = "+84987654321")
    private String phone;

    @Schema(description = "Student status", example = "Đang học")
    private String studentStatus;

    @Schema(description = "Nationality", example = "Việt Nam")
    private String nationality;

    @Schema(description = "List of addresses", implementation = AddressResponse.class)
    private List<AddressResponse> addresses;

    @Schema(description = "List of documents", implementation = DocumentResponse.class)
    private List<DocumentResponse> documents;

    @Schema(description = "Creation timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last updated timestamp", type = "string", format = "date-time", example = "2025-06-18T15:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Created by user", example = "admin")
    private String createdBy;

    @Schema(description = "Last updated by user", example = "admin")
    private String updatedBy;
}
