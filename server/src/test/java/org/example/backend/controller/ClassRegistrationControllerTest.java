package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.common.RegistrationStatus;
import org.example.backend.config.TestConfig;
import org.example.backend.dto.request.ClassRegistrationRequest;
import org.example.backend.dto.request.ClassRegistrationUpdateRequest;
import org.example.backend.dto.response.ClassRegistrationResponse;
import org.example.backend.service.IClassRegistrationService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClassRegistrationController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(TestConfig.class)
public class ClassRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClassRegistrationService classRegistrationService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClassRegistrationRequest validClassRegistrationRequest;
    private ClassRegistrationUpdateRequest validClassRegistrationUpdateRequest;
    private ClassRegistrationResponse classRegistrationResponse;
    private List<ClassRegistrationResponse> classRegistrationResponseList;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        // Setup Class Registration Request
        validClassRegistrationRequest = new ClassRegistrationRequest();
        validClassRegistrationRequest.setStatus(RegistrationStatus.REGISTERED);
        validClassRegistrationRequest.setStudentId("SV001");
        validClassRegistrationRequest.setClassId(1);

        // Setup Class Registration Update Request
        validClassRegistrationUpdateRequest = new ClassRegistrationUpdateRequest();
        validClassRegistrationUpdateRequest.setStatus(RegistrationStatus.COMPLETED);
        validClassRegistrationUpdateRequest.setGrade(8.5);

        // Setup Class Registration Response
        classRegistrationResponse = ClassRegistrationResponse.builder()
                .id(101)
                .status(RegistrationStatus.REGISTERED)
                .studentId("SV001")
                .studentName("Nguyen Van A")
                .classId(1)
                .classCode("CS101-01")
                .grade(null)
                .createdDate(now)
                .updatedDate(now)
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        // Setup additional registration for list operations
        ClassRegistrationResponse classRegistrationResponse2 = ClassRegistrationResponse.builder()
                .id(102)
                .status(RegistrationStatus.COMPLETED)
                .studentId("SV002")
                .studentName("Nguyen Van B")
                .classId(2)
                .classCode("CS102-01")
                .grade(9.0)
                .createdDate(now)
                .updatedDate(now)
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        classRegistrationResponseList = List.of(classRegistrationResponse, classRegistrationResponse2);
    }

    @Test
    @DisplayName("GET /api/class-registrations - Successfully retrieve all class registrations with pagination")
    void shouldGetAllClassRegistrationsSuccessfully() throws Exception {
        // Arrange
        Page<ClassRegistrationResponse> page = new PageImpl<>(classRegistrationResponseList);
        when(classRegistrationService.getAllClassRegistrations(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/class-registrations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Data retrieved"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(101))
                .andExpect(jsonPath("$.data[0].status").value("REGISTERED"))
                .andExpect(jsonPath("$.data[0].studentId").value("SV001"))
                .andExpect(jsonPath("$.data[0].studentName").value("Nguyen Van A"))
                .andExpect(jsonPath("$.data[0].classId").value(1))
                .andExpect(jsonPath("$.data[0].classCode").value("CS101-01"))
                .andExpect(jsonPath("$.data[1].id").value(102))
                .andExpect(jsonPath("$.data[1].status").value("COMPLETED"))
                .andExpect(jsonPath("$.data[1].studentId").value("SV002"))
                .andExpect(jsonPath("$.data[1].studentName").value("Nguyen Van B"))
                .andExpect(jsonPath("$.data[1].classId").value(2))
                .andExpect(jsonPath("$.data[1].classCode").value("CS102-01"))
                .andExpect(jsonPath("$.data[1].grade").value(9.0))
                .andExpect(jsonPath("$.paginationInfo").exists())
                .andExpect(jsonPath("$.paginationInfo.totalPages").value(1))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(0))
                .andExpect(jsonPath("$.paginationInfo.totalItems").value(2));

        // Verify service interaction
        verify(classRegistrationService, times(1)).getAllClassRegistrations(any(Pageable.class));
    }

    @Test
    @DisplayName("GET /api/class-registrations/{id} - Successfully retrieve class registration by ID")
    void shouldGetClassRegistrationByIdSuccessfully() throws Exception {
        // Arrange
        when(classRegistrationService.getClassRegistrationById(101)).thenReturn(classRegistrationResponse);

        // Act & Assert
        mockMvc.perform(get("/api/class-registrations/101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Data retrieved"))
                .andExpect(jsonPath("$.data.id").value(101))
                .andExpect(jsonPath("$.data.status").value("REGISTERED"))
                .andExpect(jsonPath("$.data.studentId").value("SV001"))
                .andExpect(jsonPath("$.data.studentName").value("Nguyen Van A"))
                .andExpect(jsonPath("$.data.classId").value(1))
                .andExpect(jsonPath("$.data.classCode").value("CS101-01"))
                .andExpect(jsonPath("$.data.grade").isEmpty())
                .andExpect(jsonPath("$.data.createdBy").value("admin"))
                .andExpect(jsonPath("$.data.updatedBy").value("admin"));

        // Verify service interaction
        verify(classRegistrationService, times(1)).getClassRegistrationById(101);
    }

    @Test
    @DisplayName("POST /api/class-registrations - Successfully create a new class registration")
    void shouldCreateClassRegistrationSuccessfully() throws Exception {
        // Arrange
        when(classRegistrationService.addClassRegistration(any(ClassRegistrationRequest.class)))
                .thenReturn(classRegistrationResponse);

        // Act & Assert
        mockMvc.perform(post("/api/class-registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validClassRegistrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value("Data created"))
                .andExpect(jsonPath("$.data.id").value(101))
                .andExpect(jsonPath("$.data.status").value("REGISTERED"))
                .andExpect(jsonPath("$.data.studentId").value("SV001"))
                .andExpect(jsonPath("$.data.studentName").value("Nguyen Van A"))
                .andExpect(jsonPath("$.data.classId").value(1))
                .andExpect(jsonPath("$.data.classCode").value("CS101-01"))
                .andExpect(jsonPath("$.data.createdBy").value("admin"))
                .andExpect(jsonPath("$.data.updatedBy").value("admin"));

        // Verify service interaction
        verify(classRegistrationService, times(1)).addClassRegistration(any(ClassRegistrationRequest.class));
    }

    @Test
    @DisplayName("POST /api/class-registrations - Validation error when student ID is blank")
    void shouldReturnValidationErrorWhenStudentIdIsBlank() throws Exception {
        // Arrange
        ClassRegistrationRequest invalidRequest = new ClassRegistrationRequest();
        invalidRequest.setStatus(RegistrationStatus.REGISTERED);
        invalidRequest.setStudentId(""); // Blank student ID
        invalidRequest.setClassId(1);

        // Act & Assert
        mockMvc.perform(post("/api/class-registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Verify service is not called
        verify(classRegistrationService, times(0)).addClassRegistration(any(ClassRegistrationRequest.class));
    }

    @Test
    @DisplayName("POST /api/class-registrations - Validation error when class ID is null")
    void shouldReturnValidationErrorWhenClassIdIsNull() throws Exception {
        // Arrange
        ClassRegistrationRequest invalidRequest = new ClassRegistrationRequest();
        invalidRequest.setStatus(RegistrationStatus.REGISTERED);
        invalidRequest.setStudentId("SV001");
        invalidRequest.setClassId(null); // Null class ID

        // Act & Assert
        mockMvc.perform(post("/api/class-registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Verify service is not called
        verify(classRegistrationService, times(0)).addClassRegistration(any(ClassRegistrationRequest.class));
    }

    @Test
    @DisplayName("PATCH /api/class-registrations/{id} - Successfully update class registration")
    void shouldUpdateClassRegistrationSuccessfully() throws Exception {
        // Arrange
        ClassRegistrationResponse updatedResponse = ClassRegistrationResponse.builder()
                .id(101)
                .status(RegistrationStatus.COMPLETED)
                .studentId("SV001")
                .studentName("Nguyen Van A")
                .classId(1)
                .classCode("CS101-01")
                .grade(8.5)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        when(classRegistrationService.updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class)))
                .thenReturn(updatedResponse);

        // Act & Assert
        mockMvc.perform(patch("/api/class-registrations/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validClassRegistrationUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Data updated"))
                .andExpect(jsonPath("$.data.id").value(101))
                .andExpect(jsonPath("$.data.status").value("COMPLETED"))
                .andExpect(jsonPath("$.data.studentId").value("SV001"))
                .andExpect(jsonPath("$.data.studentName").value("Nguyen Van A"))
                .andExpect(jsonPath("$.data.classId").value(1))
                .andExpect(jsonPath("$.data.classCode").value("CS101-01"))
                .andExpect(jsonPath("$.data.grade").value(8.5))
                .andExpect(jsonPath("$.data.createdBy").value("admin"))
                .andExpect(jsonPath("$.data.updatedBy").value("admin"));

        // Verify service interaction
        verify(classRegistrationService, times(1)).updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class));
    }

    @Test
    @DisplayName("PATCH /api/class-registrations/{id} - Validation error when status is null")
    void shouldReturnValidationErrorWhenStatusIsNull() throws Exception {
        // Arrange
        ClassRegistrationUpdateRequest invalidRequest = new ClassRegistrationUpdateRequest();
        invalidRequest.setStatus(null); // Null status
        invalidRequest.setGrade(8.5);

        // Act & Assert
        mockMvc.perform(patch("/api/class-registrations/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Verify service is not called
        verify(classRegistrationService, times(0)).updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class));
    }

    @Test
    @DisplayName("PATCH /api/class-registrations/{id} - Validation error when grade is not multiple of 0.5")
    void shouldReturnValidationErrorWhenGradeIsNotMultipleOfHalf() throws Exception {
        // Arrange
        ClassRegistrationUpdateRequest invalidRequest = new ClassRegistrationUpdateRequest();
        invalidRequest.setStatus(RegistrationStatus.COMPLETED);
        invalidRequest.setGrade(8.3); // Not a multiple of 0.5

        // Act & Assert
        mockMvc.perform(patch("/api/class-registrations/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Verify service is not called
        verify(classRegistrationService, times(0)).updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class));
    }

    @Test
    @DisplayName("PATCH /api/class-registrations/{id} - Validation error when grade is out of range")
    void shouldReturnValidationErrorWhenGradeIsOutOfRange() throws Exception {
        // Arrange
        ClassRegistrationUpdateRequest invalidRequest = new ClassRegistrationUpdateRequest();
        invalidRequest.setStatus(RegistrationStatus.COMPLETED);
        invalidRequest.setGrade(11.0); // Out of range (0-10)

        // Act & Assert
        mockMvc.perform(patch("/api/class-registrations/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        // Verify service is not called
        verify(classRegistrationService, times(0)).updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class));
    }

    @Test
    @DisplayName("PATCH /api/class-registrations/{id} - Successfully update with valid grade multiples of 0.5")
    void shouldUpdateSuccessfullyWithValidGradeMultiples() throws Exception {
        // Test various valid grades that are multiples of 0.5
        double[] validGrades = {0.0, 0.5, 1.0, 5.5, 8.5, 10.0};

        for (double validGrade : validGrades) {
            // Arrange
            ClassRegistrationUpdateRequest request = new ClassRegistrationUpdateRequest();
            request.setStatus(RegistrationStatus.COMPLETED);
            request.setGrade(validGrade);

            ClassRegistrationResponse response = ClassRegistrationResponse.builder()
                    .id(101)
                    .status(RegistrationStatus.COMPLETED)
                    .studentId("SV001")
                    .studentName("Nguyen Van A")
                    .classId(1)
                    .classCode("CS101-01")
                    .grade(validGrade)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .createdBy("admin")
                    .updatedBy("admin")
                    .build();

            when(classRegistrationService.updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class)))
                    .thenReturn(response);

            // Act & Assert
            mockMvc.perform(patch("/api/class-registrations/101")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                    .andExpect(jsonPath("$.message").value("Data updated"))
                    .andExpect(jsonPath("$.data.grade").value(validGrade));
        }

        // Verify service interaction for all valid grades
        verify(classRegistrationService, times(validGrades.length)).updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class));
    }

    @Test
    @DisplayName("GET /api/class-registrations - Successfully retrieve with default pagination")
    void shouldGetAllClassRegistrationsWithDefaultPagination() throws Exception {
        // Arrange
        Page<ClassRegistrationResponse> page = new PageImpl<>(classRegistrationResponseList.subList(0, 1));
        when(classRegistrationService.getAllClassRegistrations(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/class-registrations")
                        .param("page", "0")
                        .param("size", "3") // Default size from controller
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Data retrieved"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.paginationInfo").exists());

        // Verify service interaction
        verify(classRegistrationService, times(1)).getAllClassRegistrations(any(Pageable.class));
    }

    @Test
    @DisplayName("PATCH /api/class-registrations/{id} - Successfully update without grade")
    void shouldUpdateClassRegistrationWithoutGrade() throws Exception {
        // Arrange
        ClassRegistrationUpdateRequest requestWithoutGrade = new ClassRegistrationUpdateRequest();
        requestWithoutGrade.setStatus(RegistrationStatus.CANCELLED);
        // No grade set (null)

        ClassRegistrationResponse updatedResponse = ClassRegistrationResponse.builder()
                .id(101)
                .status(RegistrationStatus.CANCELLED)
                .studentId("SV001")
                .studentName("Nguyen Van A")
                .classId(1)
                .classCode("CS101-01")
                .grade(null) // Grade remains null
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        when(classRegistrationService.updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class)))
                .thenReturn(updatedResponse);

        // Act & Assert
        mockMvc.perform(patch("/api/class-registrations/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestWithoutGrade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Data updated"))
                .andExpect(jsonPath("$.data.id").value(101))
                .andExpect(jsonPath("$.data.status").value("CANCELLED"))
                .andExpect(jsonPath("$.data.grade").isEmpty());

        // Verify service interaction
        verify(classRegistrationService, times(1)).updateClassRegistration(eq(101), any(ClassRegistrationUpdateRequest.class));
    }
}