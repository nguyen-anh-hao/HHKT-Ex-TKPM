package org.example.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.config.TestConfig;
import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentStatusResponse;
import org.example.backend.service.IStudentStatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentStatusController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(TestConfig.class)
public class StudentStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentStatusService studentStatusService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetAllStudentStatuses() throws Exception {
        // Given
        List<StudentStatusResponse> studentStatuses = List.of(
                StudentStatusResponse.builder()
                        .id(1)
                        .studentStatusName("Active")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .createdBy("admin")
                        .updatedBy("admin")
                        .build(),
                StudentStatusResponse.builder()
                        .id(2)
                        .studentStatusName("Inactive")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .createdBy("admin")
                        .updatedBy("admin")
                        .build()
        );

        when(studentStatusService.getAllStudentStatuses()).thenReturn(studentStatuses);

        // When & Then
        mockMvc.perform(get("/api/student-statuses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].studentStatusName").value("Active"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].studentStatusName").value("Inactive"));
    }

    @Test
    public void shouldGetStudentStatusById() throws Exception {
        // Given
        StudentStatusResponse studentStatus = StudentStatusResponse.builder()
                .id(1)
                .studentStatusName("Active")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        when(studentStatusService.getStudentStatusById(1)).thenReturn(studentStatus);

        // When & Then
        mockMvc.perform(get("/api/student-statuses/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.studentStatusName").value("Active"));
    }

    @Test
    public void shouldAddStudentStatus() throws Exception {
        // Given
        StudentStatusRequest request = StudentStatusRequest.builder()
                .studentStatusName("Active")
                .build();

        StudentStatusResponse response = StudentStatusResponse.builder()
                .id(1)
                .studentStatusName("Active")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        when(studentStatusService.addStudentStatus(any(StudentStatusRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/student-statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.studentStatusName").value("Active"));
    }

    @Test
    public void shouldUpdateStudentStatus() throws Exception {
        // Given
        StudentStatusRequest request = StudentStatusRequest.builder()
                .studentStatusName("Updated Active")
                .build();

        StudentStatusResponse response = StudentStatusResponse.builder()
                .id(1)
                .studentStatusName("Updated Active")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        when(studentStatusService.updateStudentStatus(eq(1), any(StudentStatusRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/student-statuses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.studentStatusName").value("Updated Active"));
    }

    @Test
    public void shouldDeleteStudentStatus() throws Exception {
        // Given
        doNothing().when(studentStatusService).deleteStudentStatus(1);

        // When & Then
        mockMvc.perform(delete("/api/student-statuses/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}