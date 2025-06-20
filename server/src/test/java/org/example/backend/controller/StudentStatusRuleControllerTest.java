package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.config.TestConfig;
import org.example.backend.dto.request.StudentStatusRuleRequest;
import org.example.backend.dto.response.StudentStatusRuleResponse;
import org.example.backend.service.IStudentStatusRuleService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentStatusRuleController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(TestConfig.class)
public class StudentStatusRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentStatusRuleService studentStatusRuleService;

    @Autowired
    private ObjectMapper objectMapper;

    private StudentStatusRuleRequest validRequest;
    private StudentStatusRuleResponse studentStatusRuleResponse;
    private List<StudentStatusRuleResponse> studentStatusRuleResponseList;

    @BeforeEach
    void setUp() {
        // Setup Student Status Rule Request
        validRequest = new StudentStatusRuleRequest();
        validRequest.setCurrentStatusId(1);
        validRequest.setAllowedTransitionId(2);

        // Setup Student Status Rule Response
        studentStatusRuleResponse = StudentStatusRuleResponse.builder()
                .id(1)
                .currentStatusName("Đang học")
                .allowedTransitionName("Đã tốt nghiệp")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        // Setup additional student status rule for list operations
        StudentStatusRuleResponse studentStatusRuleResponse2 = StudentStatusRuleResponse.builder()
                .id(2)
                .currentStatusName("Đang học")
                .allowedTransitionName("Tạm nghỉ")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        studentStatusRuleResponseList = List.of(studentStatusRuleResponse, studentStatusRuleResponse2);
    }

    @Test
    @DisplayName("POST /api/student-status-rules - Successfully create a new student status rule")
    void shouldCreateStudentStatusRuleSuccessfully() throws Exception {
        // Arrange
        when(studentStatusRuleService.addStudentStatusRule(any(StudentStatusRuleRequest.class)))
                .thenReturn(studentStatusRuleResponse);

        // Act & Assert
        mockMvc.perform(post("/api/student-status-rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.currentStatusName").value("Đang học"))
                .andExpect(jsonPath("$.data.allowedTransitionName").value("Đã tốt nghiệp"))
                .andExpect(jsonPath("$.data.createdBy").value("admin"))
                .andExpect(jsonPath("$.data.updatedBy").value("admin"));

        // Verify service interaction
        verify(studentStatusRuleService, times(1)).addStudentStatusRule(any(StudentStatusRuleRequest.class));
    }

    @Test
    @DisplayName("POST /api/student-status-rules - Validation error for invalid request")
    void shouldReturnValidationErrorForInvalidRequest() throws Exception {
        // Arrange
        StudentStatusRuleRequest invalidRequest = new StudentStatusRuleRequest();
        // Not setting required fields to trigger validation errors

        // Act & Assert
        mockMvc.perform(post("/api/student-status-rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/student-status-rules/{id} - Successfully retrieve student status rule by ID")
    void shouldGetStudentStatusRuleByIdSuccessfully() throws Exception {
        // Arrange
        when(studentStatusRuleService.getStudentStatusRuleById(1)).thenReturn(studentStatusRuleResponse);

        // Act & Assert
        mockMvc.perform(get("/api/student-status-rules/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.currentStatusName").value("Đang học"))
                .andExpect(jsonPath("$.data.allowedTransitionName").value("Đã tốt nghiệp"))
                .andExpect(jsonPath("$.data.createdBy").value("admin"))
                .andExpect(jsonPath("$.data.updatedBy").value("admin"));

        // Verify service interaction
        verify(studentStatusRuleService, times(1)).getStudentStatusRuleById(1);
    }

    @Test
    @DisplayName("GET /api/student-status-rules/{id} - Return 404 when student status rule not found")
    void shouldReturn404WhenStudentStatusRuleNotFound() throws Exception {
        // Arrange
        when(studentStatusRuleService.getStudentStatusRuleById(999)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/student-status-rules/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Student status rule not found"))
                .andExpect(jsonPath("$.data").doesNotExist());

        // Verify service interaction
        verify(studentStatusRuleService, times(1)).getStudentStatusRuleById(999);
    }

    @Test
    @DisplayName("GET /api/student-status-rules - Successfully retrieve all student status rules with pagination")
    void shouldGetAllStudentStatusRulesSuccessfully() throws Exception {
        // Arrange
        Page<StudentStatusRuleResponse> page = new PageImpl<>(studentStatusRuleResponseList);
        when(studentStatusRuleService.getAllStudentStatusRules(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/student-status-rules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].currentStatusName").value("Đang học"))
                .andExpect(jsonPath("$.data[0].allowedTransitionName").value("Đã tốt nghiệp"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].currentStatusName").value("Đang học"))
                .andExpect(jsonPath("$.data[1].allowedTransitionName").value("Tạm nghỉ"))
                .andExpect(jsonPath("$.paginationInfo").exists())
                .andExpect(jsonPath("$.paginationInfo.totalPages").value(1))
                .andExpect(jsonPath("$.paginationInfo.currentPage").value(0))
                .andExpect(jsonPath("$.paginationInfo.totalItems").value(2));

        // Verify service interaction
        verify(studentStatusRuleService, times(1)).getAllStudentStatusRules(any(Pageable.class));
    }

    @Test
    @DisplayName("PUT /api/student-status-rules/{id} - Successfully update student status rule")
    void shouldUpdateStudentStatusRuleSuccessfully() throws Exception {
        // Arrange
        StudentStatusRuleResponse updatedResponse = StudentStatusRuleResponse.builder()
                .id(1)
                .currentStatusName("Đang học")
                .allowedTransitionName("Đã bảo lưu")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        when(studentStatusRuleService.updateStudentStatusRule(eq(1), any(StudentStatusRuleRequest.class)))
                .thenReturn(updatedResponse);

        // Act & Assert
        mockMvc.perform(put("/api/student-status-rules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.currentStatusName").value("Đang học"))
                .andExpect(jsonPath("$.data.allowedTransitionName").value("Đã bảo lưu"))
                .andExpect(jsonPath("$.data.createdBy").value("admin"))
                .andExpect(jsonPath("$.data.updatedBy").value("admin"));

        // Verify service interaction
        verify(studentStatusRuleService, times(1)).updateStudentStatusRule(eq(1), any(StudentStatusRuleRequest.class));
    }

    @Test
    @DisplayName("PUT /api/student-status-rules/{id} - Validation error for invalid request")
    void shouldReturnValidationErrorForInvalidUpdateRequest() throws Exception {
        // Arrange
        StudentStatusRuleRequest invalidRequest = new StudentStatusRuleRequest();
        // Not setting required fields to trigger validation errors

        // Act & Assert
        mockMvc.perform(put("/api/student-status-rules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/student-status-rules/{id} - Successfully delete student status rule")
    void shouldDeleteStudentStatusRuleSuccessfully() throws Exception {
        // Arrange
        when(studentStatusRuleService.getStudentStatusRuleById(1)).thenReturn(studentStatusRuleResponse);
        doNothing().when(studentStatusRuleService).deleteStudentStatusRule(1);

        // Act & Assert
        mockMvc.perform(delete("/api/student-status-rules/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"));

        // Verify service interaction
        verify(studentStatusRuleService, times(1)).getStudentStatusRuleById(1);
        verify(studentStatusRuleService, times(1)).deleteStudentStatusRule(1);
    }

    @Test
    @DisplayName("DELETE /api/student-status-rules/{id} - Return 404 when student status rule not found")
    void shouldReturn404WhenDeletingNonExistentStudentStatusRule() throws Exception {
        // Arrange
        when(studentStatusRuleService.getStudentStatusRuleById(999)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(delete("/api/student-status-rules/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Student status rule not found"));

        // Verify service interaction
        verify(studentStatusRuleService, times(1)).getStudentStatusRuleById(999);
        verify(studentStatusRuleService, times(0)).deleteStudentStatusRule(999);
    }

    @Test
    @DisplayName("GET /api/student-status-rules - Test pagination parameters")
    void shouldHandlePaginationParametersCorrectly() throws Exception {
        // Arrange
        Page<StudentStatusRuleResponse> page = new PageImpl<>(studentStatusRuleResponseList);
        when(studentStatusRuleService.getAllStudentStatusRules(any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/student-status-rules")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.paginationInfo").exists());

        // Verify service interaction
        verify(studentStatusRuleService, times(1)).getAllStudentStatusRules(any(Pageable.class));
    }

    @Test
    @DisplayName("Test invalid HTTP method")
    void shouldReturn405ForInvalidHttpMethod() throws Exception {
        // Act & Assert - Testing PATCH method which is not supported
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/student-status-rules/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Test malformed JSON request")
    void shouldReturn400ForMalformedJsonRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/student-status-rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid json }"))
                .andExpect(status().isBadRequest());
    }
}