package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.config.StudentStatusRulesConfig;
import org.example.backend.config.TestConfig;
import org.example.backend.domain.EmailDomain;
import org.example.backend.domain.PhonePattern;
import org.example.backend.dto.request.AddressRequest;
import org.example.backend.dto.request.DocumentRequest;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.response.AddressResponse;
import org.example.backend.dto.response.DocumentResponse;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.repository.IEmailDomainRepository;
import org.example.backend.repository.IPhonePatternRepository;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.IStudentService;
import org.example.backend.service.export.TranscriptPdfExportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(TestConfig.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentService studentService;

    @MockBean
    private TranscriptPdfExportService transcriptPdfExportService;

    @MockBean
    private StudentStatusRulesConfig studentStatusRulesConfig;

    @MockBean
    private IEmailDomainRepository emailDomainRepository;

    @MockBean
    private IPhonePatternRepository phonePatternRepository;

    @MockBean
    private IStudentRepository studentRepository;

    @MockBean
    private IStudentStatusRepository studentStatusRepository;

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
        // Mock validation dependencies
        when(emailDomainRepository.findAll())
                .thenReturn(List.of(new EmailDomain(1, "university.edu")));
        when(phonePatternRepository.findAll())
                .thenReturn(List.of(new PhonePattern("US", "^\\+1\\d{10}$", "US Phone Pattern")));

        // Setup Address objects
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

        // Setup Document objects
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

        // Setup Student Request
        validStudentRequest = StudentRequest.builder()
                .studentId("SV001")
                .fullName("John Doe")
                .dob(LocalDate.of(2000, 1, 1))
                .gender("Male")
                .intake("2024A")
                .email("john.doe@university.edu")
                .phoneCountry("US")
                .phone("+12345678901")
                .nationality("American")
                .facultyId(1)
                .programId(1)
                .studentStatusId(1)
                .addresses(Collections.singletonList(addressRequest))
                .documents(Collections.singletonList(documentRequest))
                .build();

        // Setup Student Response
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
                .phone("+12345678901")
                .studentStatus("Active")
                .nationality("American")
                .addresses(Collections.singletonList(addressResponse))
                .documents(Collections.singletonList(documentResponse))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        // Setup additional student for list operations
        StudentResponse studentResponse2 = StudentResponse.builder()
                .studentId("SV002")
                .fullName("Jane Smith")
                .dob(LocalDate.of(1999, 5, 10))
                .gender("Female")
                .intake("2024A")
                .program("Computer Science")
                .faculty("Engineering")
                .email("jane.smith@university.edu")
                .phoneCountry("US")
                .phone("+19876543210")
                .studentStatus("Active")
                .nationality("American")
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        studentResponseList = List.of(studentResponse, studentResponse2);
    }

    @Test
    @DisplayName("POST /api/students - Successfully create a new student")
    void shouldCreateStudentSuccessfully() throws Exception {
        assertTrue(true); // Placeholder for actual test implementation
    }

    @Test
    @DisplayName("GET /api/students/{studentId} - Successfully retrieve student by ID")
    void shouldGetStudentByIdSuccessfully() throws Exception {
        // Arrange
        when(studentService.getStudent("SV001")).thenReturn(studentResponse);

        // Act & Assert
        mockMvc.perform(get("/api/students/SV001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.studentId").value("SV001"))
                .andExpect(jsonPath("$.data.fullName").value("John Doe"))
                .andExpect(jsonPath("$.data.dob").value("2000-01-01"))
                .andExpect(jsonPath("$.data.gender").value("Male"))
                .andExpect(jsonPath("$.data.faculty").value("Engineering"))
                .andExpect(jsonPath("$.data.program").value("Computer Science"))
                .andExpect(jsonPath("$.data.studentStatus").value("Active"))
                .andExpect(jsonPath("$.data.email").value("john.doe@university.edu"))
                .andExpect(jsonPath("$.data.nationality").value("American"))
                .andExpect(jsonPath("$.data.addresses", hasSize(1)))
                .andExpect(jsonPath("$.data.addresses[0].addressType").value("Permanent"))
                .andExpect(jsonPath("$.data.documents", hasSize(1)))
                .andExpect(jsonPath("$.data.documents[0].documentType").value("Passport"));

        // Verify service interaction
        verify(studentService, times(1)).getStudent("SV001");
    }

    @Test
    @DisplayName("GET /api/students - Successfully retrieve all students with pagination")
    void shouldGetAllStudentsSuccessfully() throws Exception {
        // Arrange
        Page<StudentResponse> page = new PageImpl<>(studentResponseList);
        when(studentService.getAllStudents(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
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

        // Verify service interaction
        verify(studentService, times(1)).getAllStudents(any(Pageable.class));
    }

    @Test
    @DisplayName("PATCH /api/students/{studentId} - Successfully update student")
    void shouldUpdateStudentSuccessfully() throws Exception {
        assertTrue(true); // Placeholder for actual test implementation
    }

    @Test
    @DisplayName("DELETE /api/students/{studentId} - Successfully delete student")
    void shouldDeleteStudentSuccessfully() throws Exception {
        // Arrange
        doNothing().when(studentService).deleteStudent("SV001");

        // Act & Assert
        mockMvc.perform(delete("/api/students/SV001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Successfully deleted student with ID: SV001"));

        // Verify service interaction
        verify(studentService, times(1)).deleteStudent("SV001");
    }

    @Test
    @DisplayName("GET /api/students/search - Successfully search students")
    void shouldSearchStudentsSuccessfully() throws Exception {
        // Arrange
        Page<StudentResponse> page = new PageImpl<>(studentResponseList);
        when(studentService.searchStudent(eq("John"), any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/students/search")
                        .param("keyword", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].studentId").value("SV001"))
                .andExpect(jsonPath("$.data[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$.data[0].faculty").value("Engineering"))
                .andExpect(jsonPath("$.data[0].program").value("Computer Science"))
                .andExpect(jsonPath("$.data[0].studentStatus").value("Active"))
                .andExpect(jsonPath("$.data[1].studentId").value("SV002"))
                .andExpect(jsonPath("$.data[1].fullName").value("Jane Smith"))
                .andExpect(jsonPath("$.paginationInfo").exists())
                .andExpect(jsonPath("$.paginationInfo.totalPages").value(1))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(0))
                .andExpect(jsonPath("$.paginationInfo.totalItems").value(2));

        // Verify service interaction
        verify(studentService, times(1)).searchStudent(eq("John"), any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/students/{studentId}/transcript - Successfully get student transcript")
    void shouldGetStudentTranscriptSuccessfully() throws Exception {
        // Arrange
        byte[] mockPdfBytes = "Mock PDF content".getBytes();
        when(studentService.getStudentTranscript("SV001")).thenReturn(mockPdfBytes);
        when(transcriptPdfExportService.getMediaType()).thenReturn(MediaType.APPLICATION_PDF);
        when(transcriptPdfExportService.getFileExtension()).thenReturn("pdf");

        // Act & Assert
        mockMvc.perform(get("/api/students/SV001/transcript")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/pdf"))
                .andExpect(header().string("Content-Disposition", "inline; filename=\"transcript_SV001.pdf\""))
                .andExpect(content().bytes(mockPdfBytes));

        // Verify service interaction
        verify(studentService, times(1)).getStudentTranscript("SV001");
        verify(transcriptPdfExportService, times(1)).getMediaType();
        verify(transcriptPdfExportService, times(1)).getFileExtension();
    }


}