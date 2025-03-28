package org.example.backend.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.validator.EmailDomain;
import org.example.backend.validator.PhoneNumber;
import org.example.backend.validator.ValidStudentStatusTransition;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ValidStudentStatusTransition
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentUpdateRequest {
    @NotNull(message = "Student id is required")
    private String studentId;

    private String fullName;

    private LocalDate dob;

    private String gender;

    private String intake;

    private String email;

    private String phone;

    private String nationality;

    private Integer facultyId;

    private Integer programId;

    private Integer studentStatusId;

    private List<AddressRequest> addresses;

    private List<DocumentRequest> documents;
}
