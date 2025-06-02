package org.example.backend.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.example.backend.config.StudentStatusRulesConfig;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.repository.IStudentStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentStatusTransitionValidatorTest {

    @Mock
    private StudentStatusRulesConfig rulesConfig;

    @Mock
    private IStudentRepository studentRepository;

    @Mock
    private IStudentStatusRepository studentStatusRepository;

    @Mock
    private ConstraintValidatorContext context;

    private StudentStatusTransitionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new StudentStatusTransitionValidator(rulesConfig, studentRepository, studentStatusRepository);
    }

    @Test
    void shouldReturnTrueWhenStatusIdIsNull() {
        // Arrange
        StudentUpdateRequest request = StudentUpdateRequest.builder()
                .studentId("SV001")
                .studentStatusId(null)
                .build();

        // Act
        boolean result = validator.isValid(request, context);

        // Assert
        assertTrue(result);
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        // Arrange
        StudentUpdateRequest request = StudentUpdateRequest.builder()
                .studentId("SV001")
                .studentStatusId(1)
                .build();

        when(studentRepository.findByStudentId(request.getStudentId())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> {
            validator.isValid(request, context);
        });

        assertTrue(exception.getMessage().contains("Student not found for ID: SV001"));
    }

    @Test
    void shouldThrowExceptionWhenStudentStatusNotFound() {
        // Arrange
        Student student = new Student();
        student.setStudentStatus(new StudentStatus(1, "ACTIVE"));

        StudentUpdateRequest request = StudentUpdateRequest.builder()
                .studentId("SV001")
                .studentStatusId(2)
                .build();

        when(studentRepository.findByStudentId(request.getStudentId())).thenReturn(Optional.of(student));
        when(studentStatusRepository.findById(request.getStudentStatusId())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception =  assertThrows(IllegalArgumentException.class, () -> {
            validator.isValid(request, context);
        });

        assertTrue(exception.getMessage().contains("StudentStatus not found for ID: 2"));
    }

    private static Stream<Arguments> provideStudentStatusTransitions() {
        return Stream.of(
                Arguments.of("ACTIVE", "INACTIVE", true),
                Arguments.of("INACTIVE", "ACTIVE", false),
                Arguments.of("SUSPENDED", "ACTIVE", true),
                Arguments.of("ACTIVE", "SUSPENDED", false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStudentStatusTransitions")
    void shouldValidateStatusTransitions(String currentStatus, String newStatus, boolean isValidExpected) {
        // Arrange
        Student student = new Student();
        student.setStudentStatus(new StudentStatus(1, currentStatus));

        StudentUpdateRequest request = StudentUpdateRequest.builder()
                .studentId("SV001")
                .studentStatusId(2)
                .build();

        when(studentRepository.findByStudentId(request.getStudentId())).thenReturn(Optional.of(student));
        when(studentStatusRepository.findById(request.getStudentStatusId())).thenReturn(Optional.of(new StudentStatus(2, newStatus)));
        when(rulesConfig.isValidTransition(currentStatus, newStatus)).thenReturn(isValidExpected);

        context = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        lenient().when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
        lenient().when(builder.addConstraintViolation()).thenReturn(context);

        // Act
        boolean result = validator.isValid(request, context);

        // Assert
        assertEquals(isValidExpected, result);
    }
}
