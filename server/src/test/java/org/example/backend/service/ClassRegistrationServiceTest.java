package org.example.backend.service;

import org.example.backend.common.RegistrationStatus;
import org.example.backend.domain.Class;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.Course;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Lecturer;
import org.example.backend.domain.Program;
import org.example.backend.domain.Semester;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.ClassRegistrationHistoryRequest;
import org.example.backend.dto.request.ClassRegistrationRequest;
import org.example.backend.dto.request.ClassRegistrationUpdateRequest;
import org.example.backend.dto.response.ClassRegistrationResponse;
import org.example.backend.repository.IClassRegistrationRepository;
import org.example.backend.repository.IClassRepository;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.service.impl.ClassRegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassRegistrationServiceTest {

    @Mock
    private IClassRegistrationRepository classRegistrationRepository;

    @Mock
    private IClassRepository classRepository;

    @Mock
    private IStudentRepository studentRepository;

    @Mock
    private IClassRegistrationHistoryService classRegistrationHistoryService;

    @InjectMocks
    private ClassRegistrationServiceImpl classRegistrationService;

    private ClassRegistration testClassRegistration;
    private Class testClass;
    private Student testStudent;
    private Course testCourse;
    private Faculty testFaculty;
    private Program testProgram;
    private StudentStatus testStudentStatus;
    private Lecturer testLecturer;
    private Semester testSemester;
    private ClassRegistrationRequest registrationRequest;
    private ClassRegistrationUpdateRequest updateRequest;

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
                .classRegistrations(new ArrayList<>())
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
                .build();
        testClassRegistration.setCreatedAt(LocalDateTime.now());
        testClassRegistration.setUpdatedAt(LocalDateTime.now());
        testClassRegistration.setCreatedBy("student");
        testClassRegistration.setUpdatedBy("student");
    }

    private void setupRequests() {
        registrationRequest = new ClassRegistrationRequest();
        registrationRequest.setStatus(RegistrationStatus.REGISTERED);
        registrationRequest.setStudentId("ST001");
        registrationRequest.setClassId(1);

        updateRequest = new ClassRegistrationUpdateRequest();
        updateRequest.setStatus(RegistrationStatus.COMPLETED);
        updateRequest.setGrade(8.5);
    }

    @Test
    void getAllClassRegistrations_WithValidPageable_ShouldReturnPageOfResponses() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<ClassRegistration> registrationPage = new PageImpl<>(Arrays.asList(testClassRegistration), pageable, 1);
        when(classRegistrationRepository.findAll(pageable)).thenReturn(registrationPage);

        // When
        Page<ClassRegistrationResponse> result = classRegistrationService.getAllClassRegistrations(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        ClassRegistrationResponse response = result.getContent().get(0);
        assertEquals(testClassRegistration.getId(), response.getId());
        assertEquals(testClassRegistration.getStatus(), response.getStatus());
        assertEquals(testClassRegistration.getStudent().getStudentId(), response.getStudentId());
        assertEquals(testClassRegistration.getStudent().getFullName(), response.getStudentName());
        assertEquals(testClassRegistration.getAClass().getId(), response.getClassId());
        assertEquals(testClassRegistration.getAClass().getClassCode(), response.getClassCode());
        assertEquals(testClassRegistration.getGrade(), response.getGrade());

        verify(classRegistrationRepository).findAll(pageable);
    }

    @Test
    void getAllClassRegistrations_WithEmptyResult_ShouldReturnEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<ClassRegistration> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(classRegistrationRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<ClassRegistrationResponse> result = classRegistrationService.getAllClassRegistrations(pageable);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(classRegistrationRepository).findAll(pageable);
    }

    @Test
    void getClassRegistrationById_WithValidId_ShouldReturnResponse() {
        // Given
        Integer registrationId = 1;
        when(classRegistrationRepository.findById(registrationId)).thenReturn(Optional.of(testClassRegistration));

        // When
        ClassRegistrationResponse result = classRegistrationService.getClassRegistrationById(registrationId);

        // Then
        assertNotNull(result);
        assertEquals(testClassRegistration.getId(), result.getId());
        assertEquals(testClassRegistration.getStatus(), result.getStatus());
        assertEquals(testClassRegistration.getStudent().getStudentId(), result.getStudentId());
        assertEquals(testClassRegistration.getStudent().getFullName(), result.getStudentName());
        assertEquals(testClassRegistration.getAClass().getId(), result.getClassId());
        assertEquals(testClassRegistration.getAClass().getClassCode(), result.getClassCode());

        verify(classRegistrationRepository).findById(registrationId);
    }

    @Test
    void getClassRegistrationById_WithInvalidId_ShouldThrowException() {
        // Given
        Integer invalidId = 999;
        when(classRegistrationRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classRegistrationService.getClassRegistrationById(invalidId)
        );
        assertEquals("Class registration not found", exception.getMessage());

        verify(classRegistrationRepository).findById(invalidId);
    }

    @Test
    void addClassRegistration_WithValidRequest_ShouldCreateRegistration() {
        // Given
        when(classRepository.findById(registrationRequest.getClassId())).thenReturn(Optional.of(testClass));
        when(studentRepository.findById(registrationRequest.getStudentId())).thenReturn(Optional.of(testStudent));
        when(classRegistrationRepository.save(any(ClassRegistration.class))).thenReturn(testClassRegistration);

        // When
        ClassRegistrationResponse result = classRegistrationService.addClassRegistration(registrationRequest);

        // Then
        assertNotNull(result);
        assertEquals(testClassRegistration.getStatus(), result.getStatus());
        assertEquals(testClassRegistration.getStudent().getStudentId(), result.getStudentId());
        assertEquals(testClassRegistration.getAClass().getId(), result.getClassId());

        verify(classRepository).findById(registrationRequest.getClassId());
        verify(studentRepository).findById(registrationRequest.getStudentId());
        verify(classRegistrationRepository).save(any(ClassRegistration.class));
        verify(classRegistrationHistoryService).addClassRegistrationHistory(any(ClassRegistrationHistoryRequest.class));
    }

    @Test
    void addClassRegistration_WithInvalidClassId_ShouldThrowException() {
        // Given
        when(classRepository.findById(registrationRequest.getClassId())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classRegistrationService.addClassRegistration(registrationRequest)
        );
        assertEquals("Class not found", exception.getMessage());

        verify(classRepository).findById(registrationRequest.getClassId());
        verify(studentRepository, never()).findById(anyString());
        verify(classRegistrationRepository, never()).save(any(ClassRegistration.class));
        verify(classRegistrationHistoryService, never()).addClassRegistrationHistory(any());
    }

    @Test
    void addClassRegistration_WithInactiveCourse_ShouldThrowException() {
        // Given
        Course inactiveCourse = Course.builder()
                .id(1)
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .isActive(false)
                .faculty(testFaculty)
                .build();

        Class classWithInactiveCourse = Class.builder()
                .id(1)
                .classCode("CS101-01")
                .maxStudents(30)
                .schedule("Mon,Wed,Fri 8:00-9:30")
                .room("A101")
                .course(inactiveCourse)
                .lecturer(testLecturer)
                .semester(testSemester)
                .classRegistrations(new ArrayList<>())
                .build();

        when(classRepository.findById(registrationRequest.getClassId())).thenReturn(Optional.of(classWithInactiveCourse));

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classRegistrationService.addClassRegistration(registrationRequest)
        );
        assertEquals("Course of the class is not active", exception.getMessage());

        verify(classRepository).findById(registrationRequest.getClassId());
        verify(studentRepository, never()).findById(anyString());
        verify(classRegistrationRepository, never()).save(any(ClassRegistration.class));
    }

    @Test
    void addClassRegistration_WithMaxStudentsReached_ShouldThrowException() {
        // Given
        List<ClassRegistration> existingRegistrations = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            existingRegistrations.add(ClassRegistration.builder().build());
        }

        Class fullClass = Class.builder()
                .id(1)
                .classCode("CS101-01")
                .maxStudents(30)
                .schedule("Mon,Wed,Fri 8:00-9:30")
                .room("A101")
                .course(testCourse)
                .lecturer(testLecturer)
                .semester(testSemester)
                .classRegistrations(existingRegistrations)
                .build();

        when(classRepository.findById(registrationRequest.getClassId())).thenReturn(Optional.of(fullClass));

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classRegistrationService.addClassRegistration(registrationRequest)
        );
        assertEquals("Max students reached", exception.getMessage());

        verify(classRepository).findById(registrationRequest.getClassId());
        verify(studentRepository, never()).findById(anyString());
        verify(classRegistrationRepository, never()).save(any(ClassRegistration.class));
    }

    @Test
    void addClassRegistration_WithInvalidStudentId_ShouldThrowException() {
        // Given
        when(classRepository.findById(registrationRequest.getClassId())).thenReturn(Optional.of(testClass));
        when(studentRepository.findById(registrationRequest.getStudentId())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classRegistrationService.addClassRegistration(registrationRequest)
        );
        assertEquals("Student not found", exception.getMessage());

        verify(classRepository).findById(registrationRequest.getClassId());
        verify(studentRepository).findById(registrationRequest.getStudentId());
        verify(classRegistrationRepository, never()).save(any(ClassRegistration.class));
        verify(classRegistrationHistoryService, never()).addClassRegistrationHistory(any());
    }

    @Test
    void updateClassRegistration_WithValidRequest_ShouldUpdateRegistration() {
        // Given
        Integer registrationId = 1;
        when(classRegistrationRepository.findById(registrationId)).thenReturn(Optional.of(testClassRegistration));
        when(classRegistrationRepository.save(any(ClassRegistration.class))).thenReturn(testClassRegistration);

        // When
        ClassRegistrationResponse result = classRegistrationService.updateClassRegistration(registrationId, updateRequest);

        // Then
        assertNotNull(result);
        assertEquals(testClassRegistration.getId(), result.getId());

        verify(classRegistrationRepository).findById(registrationId);
        verify(classRegistrationRepository).save(any(ClassRegistration.class));
        verify(classRegistrationHistoryService).addClassRegistrationHistory(any(ClassRegistrationHistoryRequest.class));
    }
}