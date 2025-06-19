package org.example.backend.service;

import org.example.backend.common.RegistrationStatus;
import org.example.backend.domain.Class;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.ClassRegistrationHistory;
import org.example.backend.domain.Course;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Lecturer;
import org.example.backend.domain.Program;
import org.example.backend.domain.Semester;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.ClassRegistrationHistoryRequest;
import org.example.backend.dto.response.ClassRegistrationHistoryResponse;
import org.example.backend.repository.IClassRegistrationHistoryRepository;
import org.example.backend.repository.IClassRegistrationRepository;
import org.example.backend.service.impl.ClassRegistrationHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassRegistrationHistoryServiceTest {

    @Mock
    private IClassRegistrationHistoryRepository classRegistrationHistoryRepository;

    @Mock
    private IClassRegistrationRepository classRegistrationRepository;

    @InjectMocks
    private ClassRegistrationHistoryServiceImpl classRegistrationHistoryService;

    private ClassRegistration testClassRegistration;
    private ClassRegistrationHistory testClassRegistrationHistory;
    private ClassRegistrationHistoryRequest historyRequest;
    private Faculty testFaculty;
    private Course testCourse;
    private Lecturer testLecturer;
    private Semester testSemester;
    private Class testClass;
    private Student testStudent;
    private Program testProgram;
    private StudentStatus testStudentStatus;

    @BeforeEach
    void setUp() {
        setupTestData();
        setupRequests();
    }

    private void setupTestData() {
        testFaculty = Faculty.builder()
                .id(1)
                .facultyName("Computer Science")
                .build();

        testProgram = Program.builder()
                .id(1)
                .programName("Bachelor of Computer Science")
                .build();

        testStudentStatus = StudentStatus.builder()
                .id(1)
                .studentStatusName("Active")
                .build();

        testCourse = Course.builder()
                .id(1)
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .isActive(true)
                .faculty(testFaculty)
                .build();

        testLecturer = Lecturer.builder()
                .id(1)
                .fullName("Dr. John Smith")
                .email("john.smith@university.edu")
                .phone("0123456789")
                .faculty(testFaculty)
                .build();

        testSemester = Semester.builder()
                .id(1)
                .semester(1)
                .startDate(LocalDate.of(2024, 1, 15))
                .endDate(LocalDate.of(2024, 5, 15))
                .academicYear("2024")
                .lastCancelDate(LocalDate.of(2024, 3, 1))
                .build();

        testClass = Class.builder()
                .id(1)
                .classCode("CS101-01")
                .maxStudents(30)
                .schedule("Mon,Wed,Fri 8:00-9:30")
                .room("A101")
                .course(testCourse)
                .lecturer(testLecturer)
                .semester(testSemester)
                .classRegistrations(Collections.emptyList())
                .build();

        testStudent = Student.builder()
                .studentId("ST001")
                .fullName("John Doe")
                .dob(LocalDate.of(2000, 5, 15))
                .gender("Male")
                .intake("2022")
                .email("john.doe@student.edu")
                .phoneCountry("+84")
                .phone("0123456789")
                .nationality("Vietnamese")
                .faculty(testFaculty)
                .program(testProgram)
                .studentStatus(testStudentStatus)
                .addresses(Collections.emptyList())
                .documents(Collections.emptyList())
                .classRegistrations(Collections.emptyList())
                .build();

        testClassRegistration = ClassRegistration.builder()
                .id(1)
                .status(RegistrationStatus.REGISTERED)
                .grade(null)
                .student(testStudent)
                .aClass(testClass)
                .classRegistrationHistories(Collections.emptyList())
                .build();
        testClassRegistration.setCreatedAt(LocalDateTime.now());
        testClassRegistration.setUpdatedAt(LocalDateTime.now());
        testClassRegistration.setCreatedBy("student");
        testClassRegistration.setUpdatedBy("student");

        testClassRegistrationHistory = ClassRegistrationHistory.builder()
                .id(1)
                .action(RegistrationStatus.REGISTERED)
                .reason("Class registration created")
                .classRegistration(testClassRegistration)
                .build();
        testClassRegistrationHistory.setCreatedAt(LocalDateTime.now());
        testClassRegistrationHistory.setUpdatedAt(LocalDateTime.now());
        testClassRegistrationHistory.setCreatedBy("student");
        testClassRegistrationHistory.setUpdatedBy("student");
    }

    private void setupRequests() {
        historyRequest = ClassRegistrationHistoryRequest.builder()
                .action(RegistrationStatus.REGISTERED)
                .reason("Class registration created")
                .classRegistrationId(1)
                .build();
    }

    @Test
    void addClassRegistrationHistory_WithValidRequest_ShouldCreateHistory() {
        // Given
        when(classRegistrationRepository.findById(historyRequest.getClassRegistrationId()))
                .thenReturn(Optional.of(testClassRegistration));
        when(classRegistrationHistoryRepository.save(any(ClassRegistrationHistory.class)))
                .thenReturn(testClassRegistrationHistory);

        // When
        ClassRegistrationHistoryResponse result = classRegistrationHistoryService
                .addClassRegistrationHistory(historyRequest);

        // Then
        assertNotNull(result);
        assertEquals(testClassRegistrationHistory.getId(), result.getId());
        assertEquals(testClassRegistrationHistory.getAction(), result.getAction());
        assertEquals(testClassRegistrationHistory.getReason(), result.getReason());
        assertEquals(testClassRegistrationHistory.getClassRegistration().getId(), result.getClassRegistrationId());
        assertEquals(testClassRegistrationHistory.getClassRegistration().getAClass().getClassCode(), result.getClassCode());
        assertEquals(testClassRegistrationHistory.getClassRegistration().getStudent().getStudentId(), result.getStudentId());

        verify(classRegistrationRepository).findById(historyRequest.getClassRegistrationId());
        verify(classRegistrationHistoryRepository).save(any(ClassRegistrationHistory.class));
    }

    @Test
    void addClassRegistrationHistory_WithCompletedStatus_ShouldCreateCompletedHistory() {
        // Given
        ClassRegistrationHistoryRequest completedRequest = ClassRegistrationHistoryRequest.builder()
                .action(RegistrationStatus.COMPLETED)
                .reason("Class registration completed with grade")
                .classRegistrationId(1)
                .build();

        ClassRegistrationHistory completedHistory = ClassRegistrationHistory.builder()
                .id(2)
                .action(RegistrationStatus.COMPLETED)
                .reason("Class registration completed with grade")
                .classRegistration(testClassRegistration)
                .build();
        completedHistory.setCreatedAt(LocalDateTime.now());
        completedHistory.setUpdatedAt(LocalDateTime.now());
        completedHistory.setCreatedBy("admin");
        completedHistory.setUpdatedBy("admin");

        when(classRegistrationRepository.findById(completedRequest.getClassRegistrationId()))
                .thenReturn(Optional.of(testClassRegistration));
        when(classRegistrationHistoryRepository.save(any(ClassRegistrationHistory.class)))
                .thenReturn(completedHistory);

        // When
        ClassRegistrationHistoryResponse result = classRegistrationHistoryService
                .addClassRegistrationHistory(completedRequest);

        // Then
        assertNotNull(result);
        assertEquals(completedHistory.getId(), result.getId());
        assertEquals(RegistrationStatus.COMPLETED, result.getAction());
        assertEquals("Class registration completed with grade", result.getReason());
        assertEquals(testClassRegistration.getId(), result.getClassRegistrationId());

        verify(classRegistrationRepository).findById(completedRequest.getClassRegistrationId());
        verify(classRegistrationHistoryRepository).save(any(ClassRegistrationHistory.class));
    }

    @Test
    void addClassRegistrationHistory_WithCancelledStatus_ShouldCreateCancelledHistory() {
        // Given
        ClassRegistrationHistoryRequest cancelledRequest = ClassRegistrationHistoryRequest.builder()
                .action(RegistrationStatus.CANCELLED)
                .reason("Class registration cancelled by student")
                .classRegistrationId(1)
                .build();

        ClassRegistrationHistory cancelledHistory = ClassRegistrationHistory.builder()
                .id(3)
                .action(RegistrationStatus.CANCELLED)
                .reason("Class registration cancelled by student")
                .classRegistration(testClassRegistration)
                .build();
        cancelledHistory.setCreatedAt(LocalDateTime.now());
        cancelledHistory.setUpdatedAt(LocalDateTime.now());
        cancelledHistory.setCreatedBy("student");
        cancelledHistory.setUpdatedBy("student");

        when(classRegistrationRepository.findById(cancelledRequest.getClassRegistrationId()))
                .thenReturn(Optional.of(testClassRegistration));
        when(classRegistrationHistoryRepository.save(any(ClassRegistrationHistory.class)))
                .thenReturn(cancelledHistory);

        // When
        ClassRegistrationHistoryResponse result = classRegistrationHistoryService
                .addClassRegistrationHistory(cancelledRequest);

        // Then
        assertNotNull(result);
        assertEquals(cancelledHistory.getId(), result.getId());
        assertEquals(RegistrationStatus.CANCELLED, result.getAction());
        assertEquals("Class registration cancelled by student", result.getReason());
        assertEquals(testClassRegistration.getId(), result.getClassRegistrationId());

        verify(classRegistrationRepository).findById(cancelledRequest.getClassRegistrationId());
        verify(classRegistrationHistoryRepository).save(any(ClassRegistrationHistory.class));
    }

    @Test
    void addClassRegistrationHistory_WithDifferentClassRegistration_ShouldCreateHistoryCorrectly() {
        // Given
        ClassRegistration differentClassRegistration = ClassRegistration.builder()
                .id(2)
                .status(RegistrationStatus.REGISTERED)
                .grade(null)
                .student(testStudent)
                .aClass(testClass)
                .build();

        ClassRegistrationHistoryRequest differentRequest = ClassRegistrationHistoryRequest.builder()
                .action(RegistrationStatus.REGISTERED)
                .reason("Different class registration created")
                .classRegistrationId(2)
                .build();

        ClassRegistrationHistory differentHistory = ClassRegistrationHistory.builder()
                .id(4)
                .action(RegistrationStatus.REGISTERED)
                .reason("Different class registration created")
                .classRegistration(differentClassRegistration)
                .build();
        differentHistory.setCreatedAt(LocalDateTime.now());
        differentHistory.setUpdatedAt(LocalDateTime.now());
        differentHistory.setCreatedBy("student");
        differentHistory.setUpdatedBy("student");

        when(classRegistrationRepository.findById(differentRequest.getClassRegistrationId()))
                .thenReturn(Optional.of(differentClassRegistration));
        when(classRegistrationHistoryRepository.save(any(ClassRegistrationHistory.class)))
                .thenReturn(differentHistory);

        // When
        ClassRegistrationHistoryResponse result = classRegistrationHistoryService
                .addClassRegistrationHistory(differentRequest);

        // Then
        assertNotNull(result);
        assertEquals(differentHistory.getId(), result.getId());
        assertEquals(RegistrationStatus.REGISTERED, result.getAction());
        assertEquals("Different class registration created", result.getReason());
        assertEquals(differentClassRegistration.getId(), result.getClassRegistrationId());

        verify(classRegistrationRepository).findById(differentRequest.getClassRegistrationId());
        verify(classRegistrationHistoryRepository).save(any(ClassRegistrationHistory.class));
    }

    @Test
    void addClassRegistrationHistory_WithNullReason_ShouldCreateHistoryWithNullReason() {
        // Given
        ClassRegistrationHistoryRequest requestWithoutReason = ClassRegistrationHistoryRequest.builder()
                .action(RegistrationStatus.REGISTERED)
                .reason(null)
                .classRegistrationId(1)
                .build();

        ClassRegistrationHistory historyWithoutReason = ClassRegistrationHistory.builder()
                .id(5)
                .action(RegistrationStatus.REGISTERED)
                .reason(null)
                .classRegistration(testClassRegistration)
                .build();
        historyWithoutReason.setCreatedAt(LocalDateTime.now());
        historyWithoutReason.setUpdatedAt(LocalDateTime.now());
        historyWithoutReason.setCreatedBy("student");
        historyWithoutReason.setUpdatedBy("student");

        when(classRegistrationRepository.findById(requestWithoutReason.getClassRegistrationId()))
                .thenReturn(Optional.of(testClassRegistration));
        when(classRegistrationHistoryRepository.save(any(ClassRegistrationHistory.class)))
                .thenReturn(historyWithoutReason);

        // When
        ClassRegistrationHistoryResponse result = classRegistrationHistoryService
                .addClassRegistrationHistory(requestWithoutReason);

        // Then
        assertNotNull(result);
        assertEquals(historyWithoutReason.getId(), result.getId());
        assertEquals(RegistrationStatus.REGISTERED, result.getAction());
        assertNull(result.getReason());
        assertEquals(testClassRegistration.getId(), result.getClassRegistrationId());

        verify(classRegistrationRepository).findById(requestWithoutReason.getClassRegistrationId());
        verify(classRegistrationHistoryRepository).save(any(ClassRegistrationHistory.class));
    }

    @Test
    void addClassRegistrationHistory_ShouldSetClassRegistrationRelationshipCorrectly() {
        // Given
        when(classRegistrationRepository.findById(historyRequest.getClassRegistrationId()))
                .thenReturn(Optional.of(testClassRegistration));
        when(classRegistrationHistoryRepository.save(any(ClassRegistrationHistory.class)))
                .thenReturn(testClassRegistrationHistory);

        // When
        ClassRegistrationHistoryResponse result = classRegistrationHistoryService
                .addClassRegistrationHistory(historyRequest);

        // Then
        assertNotNull(result);
        assertEquals(testClassRegistration.getId(), result.getClassRegistrationId());
        assertEquals(testClassRegistration.getAClass().getClassCode(), result.getClassCode());
        assertEquals(testClassRegistration.getStudent().getStudentId(), result.getStudentId());

        verify(classRegistrationHistoryRepository).save(argThat(savedHistory ->
                savedHistory.getClassRegistration().equals(testClassRegistration) &&
                        savedHistory.getAction().equals(historyRequest.getAction()) &&
                        savedHistory.getReason().equals(historyRequest.getReason())
        ));
    }

    @Test
    void addClassRegistrationHistory_ShouldMapAllFieldsCorrectly() {
        // Given
        when(classRegistrationRepository.findById(historyRequest.getClassRegistrationId()))
                .thenReturn(Optional.of(testClassRegistration));
        when(classRegistrationHistoryRepository.save(any(ClassRegistrationHistory.class)))
                .thenReturn(testClassRegistrationHistory);

        // When
        ClassRegistrationHistoryResponse result = classRegistrationHistoryService
                .addClassRegistrationHistory(historyRequest);

        // Then
        assertNotNull(result);
        assertEquals(testClassRegistrationHistory.getId(), result.getId());
        assertEquals(testClassRegistrationHistory.getAction(), result.getAction());
        assertEquals(testClassRegistrationHistory.getReason(), result.getReason());
        assertEquals(testClassRegistrationHistory.getClassRegistration().getId(), result.getClassRegistrationId());
        assertEquals(testClassRegistrationHistory.getClassRegistration().getAClass().getClassCode(), result.getClassCode());
        assertEquals(testClassRegistrationHistory.getClassRegistration().getStudent().getStudentId(), result.getStudentId());
        assertEquals(testClassRegistrationHistory.getCreatedBy(), result.getCreatedBy());
        assertEquals(testClassRegistrationHistory.getUpdatedBy(), result.getUpdatedBy());

        verify(classRegistrationRepository).findById(historyRequest.getClassRegistrationId());
        verify(classRegistrationHistoryRepository).save(any(ClassRegistrationHistory.class));
    }
}