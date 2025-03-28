package org.example.backend.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Email(message = "Email should be valid")
    @EmailDomain
    private String email;

    @Pattern(regexp = "^\\+\\d{1,3}\\d{9}$", message = "Phone number should be valid. " +
            "The phone number starts with + followed by 1 to 3 digits for the country code, " +
            "and then exactly 9 digits for the phone number")
    @PhoneNumber
    private String phone;

    private String nationality;

    private Integer facultyId;

    private Integer programId;

    private Integer studentStatusId;

    private List<AddressRequest> addresses;

    private List<DocumentRequest> documents;
}
