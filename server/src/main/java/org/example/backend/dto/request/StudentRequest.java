package org.example.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.validator.EmailDomain;
import org.example.backend.validator.PhoneNumb
import jakarta.validation.constraints.Pattern;


import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class StudentRequest {
    @NotNull(message = "Student id is required")
    private String studentId;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Intake is required")
    private String intake;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @EmailDomain
    private String email;

    @NotBlank(message = "Phone number is required")
    @PhoneNumber
    private String phone;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotNull(message = "Faculty id is required")
    private Integer facultyId;

    @NotNull(message = "Program id is required")
    private Integer programId;

    @NotNull(message = "Student status id is required")
    private Integer studentStatusId;

    private List<AddressRequest> addresses;

    private List<DocumentRequest> documents;
}
