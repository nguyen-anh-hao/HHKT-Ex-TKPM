package org.example.backend.service;

import org.example.backend.domain.Faculty;
import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.FacultyResponse;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.service.impl.FacultyServiceImpl;
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
public class FacultyServiceTest {
    @Mock
    private IFacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    public void shouldGetAllFaculties() {
        List<Faculty> faculties = List.of(
                new Faculty(1, "Computer Science"),
                new Faculty(2, "Mathematics")
        );

        when(facultyRepository.findAll()).thenReturn(faculties);

        List<FacultyResponse> facultyResponses = facultyService.getAllFaculties();

        assertThat(facultyResponses).hasSize(2);
        assertThat(facultyResponses.get(0).getId()).isEqualTo(1);
        assertThat(facultyResponses.get(0).getFacultyName()).isEqualTo("Computer Science");
        assertThat(facultyResponses.get(1).getId()).isEqualTo(2);
        assertThat(facultyResponses.get(1).getFacultyName()).isEqualTo("Mathematics");
    }

    @Test
    public void shouldGetFacultyById() {
        Faculty faculty = new Faculty(1, "Computer Science");

        when(facultyRepository.findById(1)).thenReturn(Optional.of(faculty));

        FacultyResponse facultyResponse = facultyService.getFacultyById(1);

        assertThat(facultyResponse.getId()).isEqualTo(1);
        assertThat(facultyResponse.getFacultyName()).isEqualTo("Computer Science");
    }

    @Test
    public void givenFacultyIdNotFound_whenGetFacultyById_shouldThrowException() {
        when(facultyRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facultyService.getFacultyById(1);
        });

        assertThat(exception.getMessage()).isEqualTo("Faculty not found");
    }

    @Test
    public void shouldAddFaculty() {
        FacultyRequest facultyRequest = FacultyRequest.builder()
                .facultyName("Computer Science")
                .build();

        Faculty faculty = new Faculty(1, "Computer Science");

        when(facultyRepository.findByFacultyName("Computer Science")).thenReturn(Optional.empty());
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        FacultyResponse facultyResponse = facultyService.addFaculty(facultyRequest);

        assertThat(facultyResponse.getId()).isEqualTo(1);
        assertThat(facultyResponse.getFacultyName()).isEqualTo("Computer Science");
    }

    @Test
    public void givenFacultyAlreadyExists_whenAddFaculty_shouldThrowException() {
        FacultyRequest facultyRequest = FacultyRequest.builder()
                .facultyName("Computer Science")
                .build();

        when(facultyRepository.findByFacultyName("Computer Science")).thenReturn(Optional.of(new Faculty()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facultyService.addFaculty(facultyRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Faculty already exists");
    }

    @Test
    public void shouldUpdateFaculty() {
        Faculty faculty = new Faculty(1, "Computer Science");

        FacultyRequest facultyRequest = FacultyRequest.builder()
                .facultyName("Updated Computer Science")
                .build();

        when(facultyRepository.findById(1)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        FacultyResponse facultyResponse = facultyService.updateFaculty(1, facultyRequest);

        assertThat(facultyResponse.getId()).isEqualTo(1);
        assertThat(facultyResponse.getFacultyName()).isEqualTo("Updated Computer Science");
    }

    @Test
    public void givenFacultyIdNotFound_whenUpdateFaculty_shouldThrowException() {
        FacultyRequest facultyRequest = FacultyRequest.builder()
                .facultyName("Computer Science")
                .build();

        when(facultyRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facultyService.updateFaculty(1, facultyRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Faculty not found");
    }

    @Test
    public void shouldDeleteFaculty() {
        Faculty faculty = new Faculty(1, "Computer Science");

        when(facultyRepository.findById(1)).thenReturn(Optional.of(faculty));

        facultyService.deleteFaculty(1);

        verify(facultyRepository).delete(faculty);
    }

    @Test
    public void givenFacultyIdNotFound_whenDeleteFaculty_shouldThrowException() {
        when(facultyRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            facultyService.deleteFaculty(1);
        });

        assertThat(exception.getMessage()).isEqualTo("Faculty not found");
    }
}