package org.example.backend.controller;

import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ProgramResponse;
import org.example.backend.service.impl.ProgramServiceImpl;
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

@WebMvcTest(ProgramController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProgramControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgramServiceImpl programService;

    @Test
    public void shouldGetAllPrograms() throws Exception {
        List<ProgramResponse> programs = List.of(
                ProgramResponse.builder().id(1).programName("Computer Science").build(),
                ProgramResponse.builder().id(2).programName("Information Technology").build()
        );

        when(programService.getAllPrograms()).thenReturn(programs);

        mockMvc.perform(get("/api/programs"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].programName").value("Computer Science"))
                .andExpect(jsonPath("$.data[1].programName").value("Information Technology"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2));
    }

    @Test
    public void shouldGetProgramById() throws Exception {
        ProgramResponse program = ProgramResponse.builder().id(1).programName("Computer Science").build();

        when(programService.getProgramById(1)).thenReturn(program);

        mockMvc.perform(get("/api/programs/1"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.programName").value("Computer Science"));
    }

    @Test
    public void shouldAddProgram() throws Exception {
        ProgramResponse program = ProgramResponse.builder().id(1).programName("Computer Science").build();

        when(programService.addProgram(any(ProgramRequest.class))).thenReturn(program);

        mockMvc.perform(post("/api/programs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"programName\": \"Computer Science\"}"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.programName").value("Computer Science"));
    }

    @Test
    public void shouldUpdateProgram() throws Exception {
        ProgramResponse program = ProgramResponse.builder().id(1).programName("Updated Computer Science").build();

        when(programService.updateProgram(eq(1), any(ProgramRequest.class))).thenReturn(program);

        mockMvc.perform(put("/api/programs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"programName\": \"Updated Computer Science\"}"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.programName").value("Updated Computer Science"));
    }

    @Test
    public void shouldDeleteProgram() throws Exception {
        mockMvc.perform(delete("/api/programs/1"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }
}