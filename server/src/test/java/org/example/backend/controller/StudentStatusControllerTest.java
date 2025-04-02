package org.example.backend.controller;

import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentStatusResponse;
import org.example.backend.service.impl.StudentStatusServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentStatusController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class StudentStatusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentStatusServiceImpl studentStatusService;

    @Test
    public void shouldGetAllStudentStatuses() throws Exception {
        List<StudentStatusResponse> studentStatuses = List.of(
                StudentStatusResponse.builder().id(1).studentStatusName("Active").build(),
                StudentStatusResponse.builder().id(2).studentStatusName("Inactive").build()
        );

        when(studentStatusService.getAllStudentStatuses()).thenReturn(studentStatuses);

        mockMvc.perform(get("/api/student-statuses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].studentStatusName").value("Active"))
                .andExpect(jsonPath("$.data[1].studentStatusName").value("Inactive"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2));
    }

    @Test
    public void shouldGetStudentStatusById() throws Exception {
        StudentStatusResponse studentStatus = StudentStatusResponse.builder().id(1).studentStatusName("Active").build();

        when(studentStatusService.getStudentStatusById(1)).thenReturn(studentStatus);

        mockMvc.perform(get("/api/student-statuses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.studentStatusName").value("Active"));
    }

    @Test
    public void shouldAddStudentStatus() throws Exception {
        StudentStatusResponse studentStatus = StudentStatusResponse.builder().id(1).studentStatusName("Active").build();

        when(studentStatusService.addStudentStatus(any(StudentStatusRequest.class))).thenReturn(studentStatus);

        mockMvc.perform(post("/api/student-statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentStatusName\": \"Active\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.studentStatusName").value("Active"));
    }

    @Test
    public void shouldUpdateStudentStatus() throws Exception {
        StudentStatusResponse studentStatus = StudentStatusResponse.builder().id(1).studentStatusName("Updated Active").build();

        when(studentStatusService.updateStudentStatus(eq(1), any(StudentStatusRequest.class))).thenReturn(studentStatus);

        mockMvc.perform(put("/api/student-statuses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentStatusName\": \"Updated Active\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.studentStatusName").value("Updated Active"));
    }

    @Test
    public void shouldDeleteStudentStatus() throws Exception {
        mockMvc.perform(delete("/api/student-statuses/1"))
                .andExpect(status().isOk());
    }
}