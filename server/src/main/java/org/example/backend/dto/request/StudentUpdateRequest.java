package org.example.backend.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.validator.ValidStudentStatusTransition;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ValidStudentStatusTransition
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentUpdateRequest {
    @NotBlank(message = "Student id is required")
    private String studentId;

    private String fullName;

    private LocalDate dob;

    private String gender;

    private String intake;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^(0[0-9]{9})$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private String nationality;

    private Integer facultyId;

    private Integer programId;

    private Integer studentStatusId;

    private List<AddressRequest> addresses;

    private List<DocumentRequest> documents;
}
