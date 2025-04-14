package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.config.StudentStatusRulesConfig;
import org.example.backend.domain.EmailDomain;
import org.example.backend.domain.PhonePattern;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.AddressRequest;
import org.example.backend.dto.request.DocumentRequest;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.dto.response.AddressResponse;
import org.example.backend.dto.response.DocumentResponse;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.repository.IEmailDomainRepository;
import org.example.backend.repository.IPhonePatternRepository;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.IStudentService;
import org.example.backend.validator.EmailDomainValidator;
import org.example.backend.validator.PhoneNumberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentService studentService;

    @MockBean
    private EmailDomainValidator emailDomainValidator;

    @MockBean
    private PhoneNumberValidator phoneNumberValidator;

    @MockBean
    private IEmailDomainRepository emailDomainRepository;

    @MockBean
    private IPhonePatternRepository phonePatternRepository;

    @MockBean
    private StudentStatusRulesConfig studentStatusRulesConfig;

    @MockBean
    private IStudentStatusRepository studentStatusRepository;

    @MockBean
    private IStudentRepository studentRepository;


    @Autowired
    private ObjectMapper objectMapper;

    private StudentRequest validStudentRequest;
    private StudentResponse studentResponse;
    private List<StudentResponse> studentResponseList;
    private AddressRequest addressRequest;
    private DocumentRequest documentRequest;
    private AddressResponse addressResponse;
    private DocumentResponse documentResponse;


    @BeforeEach
    void setUp() {

        addressRequest = AddressRequest.builder()
                .addressType("Permanent")
                .cityProvince("New York")
                .district("Manhattan")
                .country("US")
                .wardCommune("Downtown")
                .houseNumberStreetName("123 Main St")
                .build();

        addressResponse = AddressResponse.builder()
                .addressType("Permanent")
                .cityProvince("New York")
                .district("Manhattan")
                .country("US")
                .wardCommune("Downtown")
                .houseNumberStreetName("123 Main St")
                .build();

        documentRequest = DocumentRequest.builder()
                .documentType("Passport")
                .documentNumber("A123456789")
                .issuedDate(LocalDate.of(2020, 1, 1))
                .expiredDate(LocalDate.of(2030, 1, 1))
                .issuedBy("US Government")
                .issuedCountry("US")
                .note("Valid")
                .hasChip(true)
                .build();

        documentResponse = DocumentResponse.builder()
                .documentType("Passport")
                .documentNumber("A123456789")
                .issuedDate(LocalDate.of(2020, 1, 1))
                .expiredDate(LocalDate.of(2030, 1, 1))
                .issuedBy("US Government")
                .issuedCountry("US")
                .note("Valid")
                .hasChip(true)
                .build();

        validStudentRequest = StudentRequest.builder()
                .studentId("SV001")
                .fullName("John Doe")
                .dob(LocalDate.of(2000, 1, 1))
                .gender("Male")
                .intake("2024A")
                .email("john.doe@university.edu")
                .phoneCountry("US")
                .phone("+12345678910")
                .nationality("American")
                .facultyId(1)
                .programId(1)
                .studentStatusId(1)
                .addresses(Collections.singletonList(addressRequest))
                .documents(Collections.singletonList(documentRequest))
                .build();

        studentResponse = StudentResponse.builder()
                .studentId("SV001")
                .fullName("John Doe")
                .dob(LocalDate.of(2000, 1, 1))
                .gender("Male")
                .intake("2024A")
                .program("Computer Science")
                .faculty("Engineering")
                .email("john.doe@university.edu")
                .phoneCountry("US")
                .phone("+12345678910")
                .studentStatus("Active")
                .nationality("American")
                .addresses(Collections.singletonList(addressResponse))
                .documents(Collections.singletonList(documentResponse))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        studentResponseList = new ArrayList<>();
        studentResponseList.add(studentResponse);

        StudentResponse studentResponse2 = StudentResponse.builder()
                .studentId("SV002")
                .fullName("Jane Smith")
                .dob(LocalDate.of(1999, 5, 10))
                .gender("Female")
                .intake("2024A")
                .program("Computer Science")
                .faculty("Engineering")
                .email("jane.smith@university.edu")
                .phoneCountry("+1")
                .phone("9876543210")
                .studentStatus("Active")
                .nationality("American")
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        studentResponseList.add(studentResponse2);

    when(emailDomainRepository.findAll())
            .thenReturn(List.of(
            new EmailDomain(1, "university.edu"),
                        new EmailDomain(2, "example.com")
                ));

    when(phonePatternRepository.findById(anyString()))
            .thenReturn(Optional.of(new PhonePattern("US", "^\\+1[2-9][0-9]{9}$", "United States")));


}


@Test
@DisplayName("POST /api/students - Create a new student")
public void shouldAddStudent() throws Exception {
    // Arrange: mock the service method
    when(studentService.addStudent(any(StudentRequest.class))).thenReturn(studentResponse);

    mockMvc.perform(post("/api/students")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validStudentRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
            .andExpect(jsonPath("$.data.studentId").value("SV001"))
            .andExpect(jsonPath("$.data.fullName").value("John Doe"))
            .andExpect(jsonPath("$.data.dob").value("2000-01-01"))
            .andExpect(jsonPath("$.data.gender").value("Male"))
            .andExpect(jsonPath("$.data.intake").value("2024A"))
            .andExpect(jsonPath("$.data.program").value("Computer Science"))
            .andExpect(jsonPath("$.data.faculty").value("Engineering"))
            .andExpect(jsonPath("$.data.email").value("john.doe@university.edu"))
            .andExpect(jsonPath("$.data.phoneCountry").value("US"))
            .andExpect(jsonPath("$.data.phone").value("+12345678910"))
            .andExpect(jsonPath("$.data.studentStatus").value("Active"))
            .andExpect(jsonPath("$.data.nationality").value("American"))
            .andExpect(jsonPath("$.data.addresses", hasSize(1)))
            .andExpect(jsonPath("$.data.addresses[0].addressType").value("Permanent"))
            .andExpect(jsonPath("$.data.addresses[0].cityProvince").value("New York"))
            .andExpect(jsonPath("$.data.addresses[0].district").value("Manhattan"))
            .andExpect(jsonPath("$.data.addresses[0].country").value("US"))
            .andExpect(jsonPath("$.data.addresses[0].wardCommune").value("Downtown"))
            .andExpect(jsonPath("$.data.addresses[0].houseNumberStreetName").value("123 Main St"))
            .andExpect(jsonPath("$.data.documents", hasSize(1)))
            .andExpect(jsonPath("$.data.documents[0].documentType").value("Passport"))
            .andExpect(jsonPath("$.data.documents[0].documentNumber").value("A123456789"))
            .andExpect(jsonPath("$.data.documents[0].issuedDate").value("2020-01-01"))
            .andExpect(jsonPath("$.data.documents[0].expiredDate").value("2030-01-01"))
            .andExpect(jsonPath("$.data.documents[0].issuedBy").value("US Government"))
            .andExpect(jsonPath("$.data.documents[0].issuedCountry").value("US"))
            .andExpect(jsonPath("$.data.documents[0].note").value("Valid"));

    verify(studentService, times(1)).addStudent(any(StudentRequest.class));
}

@Test
@DisplayName("POST /api/students - Validation Errors")
public void shouldFailValidationWhenRequiredFieldsAreMissing() throws Exception {
        // Arrange: clone and wipe required fields
        StudentRequest invalidRequest = cloneValidRequestWithChange(request -> {
            request.setStudentId(null);
            request.setFullName(null);
            request.setDob(null);
            request.setGender(null);
            request.setIntake(null);
            request.setEmail(null);
            request.setPhoneCountry(null);
            request.setPhone(null);
            request.setNationality(null);
            return request;
        });

        // Act & Assert: perform the request and check for validation errors
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
//                .andExpect(jsonPath("$.errors", hasSize(8)))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Student id is required"))))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Full name is required"))))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Date of birth is required"))))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Gender is required"))))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Intake is required"))))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Email is required"))))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Phone country is required"))))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Phone number is required"))))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Nationality is required"))));

        // Verify that the service method was never called
        verify(studentService, never()).addStudent(any(StudentRequest.class));
    }

    @Test
    @DisplayName(("POST /api/students - Invalid Email Domain"))
    void shouldFailValidationForInvalidEmailDomain() throws Exception {
        //Arrange: clone and set invalid email domain
        StudentRequest invalidEmailRequest = cloneValidRequestWithChange(request -> {
                request.setEmail("john.doe@gmail.com");
                return request;
        });

        when(emailDomainValidator.isValid(eq("john.doe@gmail.com"), any())).thenReturn(false);


        // Act & Assert: perform the request and check for validation errors
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailRequest)))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Invalid email domain"))))
//                .andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));

        verify(studentService, never()).addStudent(any(StudentRequest.class));
    }

    @Test
    @DisplayName("POST /api/students - Invalid Email Format")
    void shouldFailValidationWhenEmailFormatIsInvalid() throws Exception {
        // Arrange: clone and set invalid email format
        StudentRequest invalidEmailRequest = cloneValidRequestWithChange(request -> {
            request.setEmail("john.doe@university");
            return request;
        });

        // Act & Assert: perform the request and check for validation errors
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailRequest)))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
//                .andExpect(jsonPath("$.errors", hasItem("Email should be valid")));

        verify(studentService, never()).addStudent(any(StudentRequest.class));
    }

    @Test
    @DisplayName("POST /api/students - Invalid Phone Number")
    void shouldFailValidationWhenPhoneNumberIsInvalid() throws Exception {
        // Arrange: clone and set invalid phone number
        StudentRequest invalidPhoneRequest = cloneValidRequestWithChange(request -> {
            request.setPhone("invalid-phone");
            return request;
        });

        // Act & Assert: perform the request and check for validation errors
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPhoneRequest)))
                .andExpect(status().isBadRequest());
        //       .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
        //       .andExpect(jsonPath("$.errors", hasItem("Invalid phone number format")));

        verify(studentService, never()).addStudent(any(StudentRequest.class));
    }

    @Test
    @DisplayName("POST /api/students - Unsupported Country Code for Phone")
    void shouldRejectUnsupportedPhoneCountryCode() throws Exception {
        // Arrange: create a request with a non-existent country code
        StudentRequest unsupportedCountryRequest = cloneValidRequestWithChange(request -> {
            request.setPhoneCountry("XYZ"); // Non-existent country code
            request.setPhone("+12345678910");
            return request;
        });

        when(phonePatternRepository.findById("XYZ")).thenReturn(Optional.empty());


        // Act & Assert: perform the request and check for validation error
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(unsupportedCountryRequest)))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Unsupported country code: XYZ"))));

        // Verify that the service method was never called
        verify(studentService, never()).addStudent(any(StudentRequest.class));
    }

    @Test
    @DisplayName("POST /api/students - Invalid US Phone Number Format")
    void shouldRejectInvalidUSPhoneNumberFormat() throws Exception {
        // Arrange: create a request with invalid US phone format
        StudentRequest invalidUSPhoneRequest = cloneValidRequestWithChange(request -> {
            request.setPhoneCountry("US");
            request.setPhone("123-ABC-4567"); // Invalid US format with letters
            return request;
        });

        // Act & Assert: perform the request and check for the specific validation error
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUSPhoneRequest)))
                .andExpect(status().isBadRequest());
//                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
//                .andExpect(jsonPath("$.errors", hasItem(containsString("Invalid phone number format for US"))));

        // Verify that the service method was never called due to validation failure
        verify(studentService, never()).addStudent(any(StudentRequest.class));
    }

    @Test
    @DisplayName("GET /api/students/{studentId} - Get student by ID")
    public void shouldGetStudentById() throws Exception {
        // Arrange: mock the service method
        when(studentService.getStudent(anyString())).thenReturn(studentResponse);

        // Act & Assert: perform the request and check the response
        mockMvc.perform(get("/api/students/SV001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.studentId").value("SV001"))
                .andExpect(jsonPath("$.data.fullName").value("John Doe"))
                .andExpect(jsonPath("$.data.dob").value("2000-01-01"))
                .andExpect(jsonPath("$.data.faculty").value("Engineering"))
                .andExpect(jsonPath("$.data.program").value("Computer Science"))
                .andExpect(jsonPath("$.data.addresses", hasSize(1)))
                .andExpect(jsonPath("$.data.addresses[0].addressType").value("Permanent"))
                .andExpect(jsonPath("$.data.documents", hasSize(1)))
                .andExpect(jsonPath("$.data.documents[0].documentType").value("Passport"));

        verify(studentService, times(1)).getStudent("SV001");
    }

    @Test
    @DisplayName("GET /api/students - Get all students")
    public void shouldGetAllStudent() throws Exception {
        // Arrange: mock the service method
        Page<StudentResponse> page = new PageImpl<>(studentResponseList);
        when(studentService.getAllStudents(any(Pageable.class))).thenReturn(page);

        // Act & Assert: perform the request and check the response
        mockMvc.perform(get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].studentId").value("SV001"))
                .andExpect(jsonPath("$.data[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$.data[0].faculty").value("Engineering"))
                .andExpect(jsonPath("$.data[0].program").value("Computer Science"))
                .andExpect(jsonPath("$.data[0].studentStatus").value("Active"))
                .andExpect(jsonPath("$.data[1].studentId").value("SV002"))
                .andExpect(jsonPath("$.data[1].fullName").value("Jane Smith"))
                .andExpect(jsonPath("$.data[1].faculty").value("Engineering"))
                .andExpect(jsonPath("$.data[1].program").value("Computer Science"))
                .andExpect(jsonPath("$.data[1].studentStatus").value("Active"))
                .andExpect(jsonPath("$.paginationInfo").exists())
                .andExpect(jsonPath("$.paginationInfo.totalPages").value(1))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(0))
                .andExpect(jsonPath("$.paginationInfo.totalItems").value(2));

        verify(studentService, times(1)).getAllStudents(any(Pageable.class));
    }

    @Test
    @DisplayName("PATCH /api/students/{studentId} - Update student with status transition validation")
    public void shouldUpdateStudent() throws Exception {
        // Arrange: mock the service method
        StudentUpdateRequest updateRequest = StudentUpdateRequest.builder()
                .studentId("SV001") // Ensure studentId is set
                .fullName("John Doe Updated")
                .studentStatusId(123) // Mock a new student status ID
                .build();

        StudentResponse updatedStudentResponse = cloneStudentResponseWithChange(response -> {
            response.setFullName("John Doe Updated");
            response.setStudentStatus("GRADUATED");
            return response;
        });

        // Mock the studentRepository to return a student with the current status
        Student student = new Student();
        student.setStudentId("SV001");
        student.setStudentStatus(new StudentStatus("ACTIVE"));
        when(studentRepository.findByStudentId("SV001")).thenReturn(Optional.of(student));

        // Mock the studentStatusRepository to return the new status
        StudentStatus newStatus = new StudentStatus("GRADUATED");
        when(studentStatusRepository.findById(123)).thenReturn(Optional.of(newStatus));

        // Mock the transition validation
        when(studentStatusRulesConfig.isValidTransition("ACTIVE", "GRADUATED")).thenReturn(true);

        // Mock the updateStudent service method to return the updated student response
        when(studentService.updateStudent(eq("SV001"), argThat(request ->
                request.getFullName().equals("John Doe Updated") &&
                        request.getStudentStatusId() == 123))).thenReturn(updatedStudentResponse);

        // Act & Assert: perform the request and check the response
        mockMvc.perform(patch("/api/students/SV001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.studentId").value("SV001"))
                .andExpect(jsonPath("$.data.fullName").value("John Doe Updated"))
                .andExpect(jsonPath("$.data.studentStatus").value("GRADUATED"));

        // Verify the service method was called once with correct arguments
        verify(studentService, times(1)).updateStudent(eq("SV001"), argThat(request ->
                request.getFullName().equals("John Doe Updated") &&
                        request.getStudentStatusId() == 123));
    }

    @Test
    @DisplayName("DELETE /api/students/{studentId} - Delete student")
    public void shouldDeleteStudent() throws Exception {
        // Arrange: mock the service method
        doNothing().when(studentService).deleteStudent(anyString());

        // Act & Assert: perform the request and check the response
        mockMvc.perform(delete("/api/students/SV001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Successfully deleted student with ID: SV001"));
        ;

        verify(studentService, times(1)).deleteStudent("SV001");
    }

    @Test
    @DisplayName("GET /api/students/search - Search students")
    public void shouldSearchStudents() throws Exception {
        // Arrange: mock the service method
        Page<StudentResponse> page = new PageImpl<>(studentResponseList);
        when(studentService.searchStudent(anyString(), any(Pageable.class))).thenReturn(page);

        // Act & Assert: perform the request and check the response
        mockMvc.perform(get("/api/students/search")
                        .param("keyword", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].studentId").value("SV001"))
                .andExpect(jsonPath("$.data[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$.data[0].faculty").value("Engineering"))
                .andExpect(jsonPath("$.data[0].program").value("Computer Science"))
                .andExpect(jsonPath("$.data[0].studentStatus").value("Active"))
                .andExpect(jsonPath("$.paginationInfo").exists())
                .andExpect(jsonPath("$.paginationInfo.totalPages").value(1))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(0))
                .andExpect(jsonPath("$.paginationInfo.totalItems").value(2));

        verify(studentService, times(1)).searchStudent(anyString(), any(Pageable.class));
    }

    // Helper method to create a modified clone of the valid request
    private StudentRequest cloneValidRequestWithChange(Function<StudentRequest, StudentRequest> modifier) {
        StudentRequest clone = StudentRequest.builder()
                .studentId(validStudentRequest.getStudentId())
                .fullName(validStudentRequest.getFullName())
                .dob(validStudentRequest.getDob())
                .gender(validStudentRequest.getGender())
                .intake(validStudentRequest.getIntake())
                .email(validStudentRequest.getEmail())
                .phoneCountry(validStudentRequest.getPhoneCountry())
                .phone(validStudentRequest.getPhone())
                .nationality(validStudentRequest.getNationality())
                .facultyId(validStudentRequest.getFacultyId())
                .programId(validStudentRequest.getProgramId())
                .studentStatusId(validStudentRequest.getStudentStatusId())
                .addresses(validStudentRequest.getAddresses())
                .documents(validStudentRequest.getDocuments())
                .build();

        return modifier.apply(clone);
    }

    private StudentResponse cloneStudentResponseWithChange(Function<StudentResponse, StudentResponse> modifier) {
        // help me complete the code
        StudentResponse clone = StudentResponse.builder()
                .studentId(studentResponse.getStudentId())
                .fullName(studentResponse.getFullName())
                .dob(studentResponse.getDob())
                .gender(studentResponse.getGender())
                .intake(studentResponse.getIntake())
                .program(studentResponse.getProgram())
                .faculty(studentResponse.getFaculty())
                .email(studentResponse.getEmail())
                .phoneCountry(studentResponse.getPhoneCountry())
                .phone(studentResponse.getPhone())
                .studentStatus(studentResponse.getStudentStatus())
                .nationality(studentResponse.getNationality())
                .addresses(studentResponse.getAddresses())
                .documents(studentResponse.getDocuments())
                .createdAt(studentResponse.getCreatedAt())
                .updatedAt(studentResponse.getUpdatedAt())
                .createdBy(studentResponse.getCreatedBy())
                .updatedBy(studentResponse.getUpdatedBy())
                .build();

        return modifier.apply(clone);
    }
}
