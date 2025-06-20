package org.example.backend.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.validator.EmailDomain;
import org.example.backend.validator.PhoneNumber;
import org.example.backend.validator.ValidStudentStatusTransition;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@ValidStudentStatusTransition
@PhoneNumber
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentUpdateRequest {
    @NotNull(message = "Student id is required")
    @Schema(description = "Unique student ID", example = "SV001")
    private String studentId;

    @Schema(description = "Full name", example = "Alice Nguyen")
    private String fullName;

    @Schema(description = "Date of birth", example = "2003-04-15")
    private LocalDate dob;

    @Schema(description = "Gender of the student", example = "Nam")
    private String gender;

    @Schema(description = "Intake batch or semester", example = "K20")
    private String intake;

    @Schema(description = "Email address", example = "alice@example.com")
    @Email(message = "Email should be valid")
    @EmailDomain
    private String email;

    @Schema(description = "Phone country code", example = "VN")
    private String phoneCountry;

    @Schema(description = "Phone number", example = "+84987654321")
    private String phone;

    @Schema(description = "Nationality", example = "Việt Nam")
    private String nationality;

    @Schema(description = "Faculty ID", example = "1")
    private Integer facultyId;

    @Schema(description = "Program ID", example = "2")
    private Integer programId;

    @Schema(description = "Student status ID", example = "3")
    private Integer studentStatusId;

    @Schema(description = "List of addresses")
    private List<AddressRequest> addresses;

    @Schema(description = "List of documents")
    private List<DocumentRequest> documents;
}
