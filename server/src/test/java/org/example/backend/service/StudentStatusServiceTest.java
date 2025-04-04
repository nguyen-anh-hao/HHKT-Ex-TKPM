package org.example.backend.service;

import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentStatusResponse;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.impl.StudentStatusServiceImpl;
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
public class StudentStatusServiceTest {
    @Mock
    private IStudentStatusRepository studentStatusRepository;

    @InjectMocks
    private StudentStatusServiceImpl studentStatusService;

    @Test
    public void shouldGetAllStudentStatuses() {
        List<StudentStatus> studentStatuses = List.of(
                new StudentStatus(1, "Active"),
                new StudentStatus(2, "Inactive")
        );

        when(studentStatusRepository.findAll()).thenReturn(studentStatuses);

        List<StudentStatusResponse> studentStatusResponses = studentStatusService.getAllStudentStatuses();

        assertThat(studentStatusResponses).hasSize(2);
        assertThat(studentStatusResponses.get(0).getId()).isEqualTo(1);
        assertThat(studentStatusResponses.get(0).getStudentStatusName()).isEqualTo("Active");
        assertThat(studentStatusResponses.get(1).getId()).isEqualTo(2);
        assertThat(studentStatusResponses.get(1).getStudentStatusName()).isEqualTo("Inactive");
    }

    @Test
    public void shouldGetStudentStatusById() {
        StudentStatus studentStatus = new StudentStatus(1, "Active");

        when(studentStatusRepository.findById(1)).thenReturn(Optional.of(studentStatus));

        StudentStatusResponse studentStatusResponse = studentStatusService.getStudentStatusById(1);

        assertThat(studentStatusResponse.getId()).isEqualTo(1);
        assertThat(studentStatusResponse.getStudentStatusName()).isEqualTo("Active");
    }

    @Test
    public void givenStudentStatusIdNotFound_whenGetStudentStatusById_shouldThrowException() {
        when(studentStatusRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentStatusService.getStudentStatusById(1);
        });

        assertThat(exception.getMessage()).isEqualTo("Student status not found");
    }

    @Test
    public void shouldAddStudentStatus() {
        StudentStatusRequest studentStatusRequest = StudentStatusRequest.builder()
                .studentStatusName("Active")
                .build();

        StudentStatus studentStatus = new StudentStatus(1, "Active");

        when(studentStatusRepository.findByStudentStatusName("Active")).thenReturn(Optional.empty());
        when(studentStatusRepository.save(any(StudentStatus.class))).thenReturn(studentStatus);

        StudentStatusResponse studentStatusResponse = studentStatusService.addStudentStatus(studentStatusRequest);

        assertThat(studentStatusResponse.getId()).isEqualTo(1);
        assertThat(studentStatusResponse.getStudentStatusName()).isEqualTo("Active");
    }

    @Test
    public void givenStudentStatusAlreadyExists_whenAddStudentStatus_shouldThrowException() {
        StudentStatusRequest studentStatusRequest = StudentStatusRequest.builder()
                .studentStatusName("Active")
                .build();

        when(studentStatusRepository.findByStudentStatusName("Active")).thenReturn(Optional.of(new StudentStatus()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentStatusService.addStudentStatus(studentStatusRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Student status already exists");
    }

    @Test
    public void shouldUpdateStudentStatus() {
        StudentStatus studentStatus = new StudentStatus(1, "Active");
        StudentStatus updatedStudentStatus = new StudentStatus(1, "On Leave");

        StudentStatusRequest studentStatusRequest = StudentStatusRequest.builder()
                .studentStatusName("On Leave")
                .build();

        when(studentStatusRepository.findById(1)).thenReturn(Optional.of(studentStatus));
        when(studentStatusRepository.save(any(StudentStatus.class))).thenReturn(updatedStudentStatus);

        StudentStatusResponse studentStatusResponse = studentStatusService.updateStudentStatus(1, studentStatusRequest);

        assertThat(studentStatusResponse.getId()).isEqualTo(1);
        assertThat(studentStatusResponse.getStudentStatusName()).isEqualTo("On Leave");
    }

    @Test
    public void givenStudentStatusIdNotFound_whenUpdateStudentStatus_shouldThrowException() {
        StudentStatusRequest studentStatusRequest = StudentStatusRequest.builder()
                .studentStatusName("Active")
                .build();

        when(studentStatusRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentStatusService.updateStudentStatus(1, studentStatusRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("Student status not found");
    }

    @Test
    public void shouldDeleteStudentStatus() {
        StudentStatus studentStatus = new StudentStatus(1, "Active");

        when(studentStatusRepository.findById(1)).thenReturn(Optional.of(studentStatus));

        studentStatusService.deleteStudentStatus(1);

        verify(studentStatusRepository).delete(studentStatus);
    }

    @Test
    public void givenStudentStatusIdNotFound_whenDeleteStudentStatus_shouldThrowException() {
        when(studentStatusRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentStatusService.deleteStudentStatus(1);
        });

        assertThat(exception.getMessage()).isEqualTo("Student status not found");
    }
}