package org.example.backend.controller;

import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.FacultyResponse;
import org.example.backend.service.impl.FacultyServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
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

@WebMvcTest(FacultyController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyServiceImpl facultyService;

    @Test
    public void shouldGetAllFaculties() throws Exception {
        List<FacultyResponse> faculties = List.of(
                FacultyResponse.builder().id(1).facultyName("Computer Science").build(),
                FacultyResponse.builder().id(2).facultyName("Mathematics").build()
        );

        when(facultyService.getAllFaculties()).thenReturn(faculties);

        mockMvc.perform(get("/api/faculties"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].facultyName").value("Computer Science"))
                .andExpect(jsonPath("$.data[1].facultyName").value("Mathematics"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2));
    }

    @Test
    public void shouldGetFacultyById() throws Exception {
        FacultyResponse faculty = FacultyResponse.builder().id(1).facultyName("Computer Science").build();

        when(facultyService.getFacultyById(1)).thenReturn(faculty);

        mockMvc.perform(get("/api/faculties/1"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.facultyName").value("Computer Science"));
    }

    @Test
    public void shouldAddFaculty() throws Exception {
        FacultyResponse faculty = FacultyResponse.builder().id(1).facultyName("Computer Science").build();

        when(facultyService.addFaculty(any(FacultyRequest.class))).thenReturn(faculty);

        mockMvc.perform(post("/api/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"facultyName\": \"Computer Science\"}"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.facultyName").value("Computer Science"));
    }

    @Test
    public void shouldUpdateFaculty() throws Exception {
        FacultyResponse faculty = FacultyResponse.builder().id(1).facultyName("Updated Computer Science").build();

        when(facultyService.updateFaculty(eq(1), any(FacultyRequest.class))).thenReturn(faculty);

        mockMvc.perform(put("/api/faculties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"facultyName\": \"Updated Computer Science\"}"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.facultyName").value("Updated Computer Science"));
    }

    @Test
    public void shouldDeleteFaculty() throws Exception {
        mockMvc.perform(delete("/api/faculties/1"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }
}