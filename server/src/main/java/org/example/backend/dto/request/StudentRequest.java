package org.example.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.validator.EmailDomain;
import org.example.backend.validator.PhoneNumber;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@PhoneNumber
public class StudentRequest {
    @NotNull(message = "Student id is required")
    @Schema(description = "Unique student ID", example = "SV001")
    private String studentId;

    @NotBlank(message = "Full name is required")
    @Schema(description = "Full name of the student", example = "Alice Nguyen")
    private String fullName;

    @NotNull(message = "Date of birth is required")
    @Schema(description = "Date of birth", example = "2003-04-15")
    private LocalDate dob;

    @NotBlank(message = "Gender is required")
    @Schema(description = "Gender of the student", example = "Nam")
    private String gender;

    @NotBlank(message = "Intake is required")
    @Schema(description = "Intake batch or semester", example = "K20")
    private String intake;

    @NotBlank(message = "Email is required")
    @Schema(description = "Email address", example = "alice@example.com")
    @Email(message = "Email should be valid")
    @EmailDomain
    private String email;

    @NotBlank(message = "Phone country is required")
    @Schema(description = "Phone country code", example = "VN")
    private String phoneCountry;

    @NotBlank(message = "Phone number is required")
    @Schema(description = "Phone number", example = "+84987654321")
    private String phone;

    @NotBlank(message = "Nationality is required")
    @Schema(description = "Nationality", example = "Việt Nam")
    private String nationality;

    @NotNull(message = "Faculty id is required")
    @Schema(description = "Faculty ID", example = "1")
    private Integer facultyId;

    @NotNull(message = "Program id is required")
    @Schema(description = "Program ID", example = "101")
    private Integer programId;

    @NotNull(message = "Student status id is required")
    @Schema(description = "Student status ID", example = "2")
    private Integer studentStatusId;

    @Schema(description = "List of student addresses")
    private List<AddressRequest> addresses;

    @Schema(description = "List of documents submitted by student")
    private List<DocumentRequest> documents;
}