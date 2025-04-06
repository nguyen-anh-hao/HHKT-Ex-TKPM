package org.example.backend.service;

import org.example.backend.domain.Program;
import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ProgramResponse;
import org.example.backend.repository.IProgramRepository;
import org.example.backend.service.impl.ProgramServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {
    @Mock
    private IProgramRepository programRepository;

    @InjectMocks
    private ProgramServiceImpl programService;

    @Test
    public void shouldGetAllPrograms() {
        List<Program> programs = List.of(
                new Program(1, "Computer Science"),
                new Program(2, "Information Technology")
        );

        when(programRepository.findAll()).thenReturn(programs);

        List<ProgramResponse> programResponses = programService.getAllPrograms();

        assertThat(programResponses).hasSize(2);
        assertThat(programResponses.get(0).getId()).isEqualTo(1);
        assertThat(programResponses.get(0).getProgramName()).isEqualTo("Computer Science");
        assertThat(programResponses.get(1).getId()).isEqualTo(2);
        assertThat(programResponses.get(1).getProgramName()).isEqualTo("Information Technology");
    }

    @Test
    public void shouldGetProgramById() {
        Program program = new Program(1, "Computer Science");

        when(programRepository.findById(1)).thenReturn(Optional.of(program));

        ProgramResponse programResponse = programService.getProgramById(1);

        assertThat(programResponse.getId()).isEqualTo(1);
        assertThat(programResponse.getProgramName()).isEqualTo("Computer Science");
    }

    @Test
    public void givenProgramIdNotFound_whenGetProgramById_shouldThrowException() {
        when(programRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            programService.getProgramById(1);
        });

        assertThat(exception.getMessage()).isEqualTo("Program not found");
    }

    @Test
    public void shouldAddProgram() {
        ProgramRequest programRequest = ProgramRequest.builder()
                .programName("Computer Science")
                .build();

        Program program = new Program(1, "Computer Science");

        when(programRepository.findByProgramName("Computer Science")).thenReturn(Optional.empty());
        when(programRepository.save(any(Program.class))).thenReturn(program);

        ProgramResponse programResponse = programService.addProgram(programRequest);

        assertThat(programResponse.getId()).isEqualTo(1);
        assertThat(programResponse.getProgramName()).isEqualTo("Computer Science");
    }

    @Test
    public void givenProgramAlreadyExists_whenAddProgram_shouldThrowException() {
        ProgramRequest programRequest = ProgramRequest.builder()
                .programName("Computer Science")
                .build();

        when(programRepository.findByProgramName("Computer Science")).thenReturn(Optional.of(new Program()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            programService.addProgram(programRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Program already exists");
    }

    @Test
    public void shouldUpdateProgram() {
        Program program = new Program(1, "Computer Science");

        ProgramRequest programRequest = ProgramRequest.builder()
                .programName("Updated Computer Science")
                .build();

        when(programRepository.findById(1)).thenReturn(Optional.of(program));
        when(programRepository.save(any(Program.class))).thenReturn(program);

        ProgramResponse programResponse = programService.updateProgram(1, programRequest);

        assertThat(programResponse.getId()).isEqualTo(1);
        assertThat(programResponse.getProgramName()).isEqualTo("Updated Computer Science");
    }

    @Test
    public void givenProgramIdNotFound_whenUpdateProgram_shouldThrowException() {
        ProgramRequest programRequest = ProgramRequest.builder()
                .programName("Computer Science")
                .build();

        when(programRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            programService.updateProgram(1, programRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Program not found");
    }

    @Test
    public void shouldDeleteProgram() {
        Program program = new Program(1, "Computer Science");

        when(programRepository.findById(1)).thenReturn(Optional.of(program));

        programService.deleteProgram(1);

        verify(programRepository).delete(program);
    }

    @Test
    public void givenProgramIdNotFound_whenDeleteProgram_shouldThrowException() {
        when(programRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            programService.deleteProgram(1);
        });

        assertThat(exception.getMessage()).isEqualTo("Program not found");
    }
}