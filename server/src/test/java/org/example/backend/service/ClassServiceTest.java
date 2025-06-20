package org.example.backend.service;

import org.example.backend.domain.Class;
import org.example.backend.domain.Course;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Lecturer;
import org.example.backend.domain.Semester;
import org.example.backend.dto.request.ClassRequest;
import org.example.backend.dto.response.ClassResponse;
import org.example.backend.repository.IClassRepository;
import org.example.backend.repository.ICourseRepository;
import org.example.backend.repository.ILecturerRepository;
import org.example.backend.repository.ISemesterRepository;
import org.example.backend.service.impl.ClassServiceImpl;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassServiceTest {

    @Mock
    private IClassRepository classRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private ILecturerRepository lecturerRepository;

    @Mock
    private ISemesterRepository semesterRepository;

    @InjectMocks
    private ClassServiceImpl classService;

    private Class testClass;
    private Course testCourse;
    private Lecturer testLecturer;
    private Semester testSemester;
    private Faculty testFaculty;
    private ClassRequest classRequest;

    @BeforeEach
    void setUp() {
        testFaculty = Faculty.builder()
                .id(1)
                .facultyName("Computer Science")
                .build();

        testCourse = Course.builder()
                .id(1)
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .description("Basic computer science course")
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
        testClass.setCreatedAt(LocalDateTime.now());
        testClass.setUpdatedAt(LocalDateTime.now());
        testClass.setCreatedBy("admin");
        testClass.setUpdatedBy("admin");

        classRequest = new ClassRequest();
        classRequest.setClassCode("CS101-01");
        classRequest.setMaxStudents(30);
        classRequest.setSchedule("Mon,Wed,Fri 8:00-9:30");
        classRequest.setRoom("A101");
        classRequest.setCourseId(1);
        classRequest.setLecturerId(1);
        classRequest.setSemesterId(1);
    }

    @Test
    void getAllClasses_WithValidPageable_ShouldReturnPageOfClassResponses() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Class> classPage = new PageImpl<>(Arrays.asList(testClass), pageable, 1);
        when(classRepository.findAll(pageable)).thenReturn(classPage);

        // When
        Page<ClassResponse> result = classService.getAllClasses(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        ClassResponse classResponse = result.getContent().get(0);
        assertEquals(testClass.getId(), classResponse.getId());
        assertEquals(testClass.getClassCode(), classResponse.getClassCode());
        assertEquals(testClass.getMaxStudents(), classResponse.getMaxStudents());
        assertEquals(testClass.getSchedule(), classResponse.getSchedule());
        assertEquals(testClass.getRoom(), classResponse.getRoom());
        assertEquals(testClass.getCourse().getId(), classResponse.getCourseId());
        assertEquals(testClass.getCourse().getCourseName(), classResponse.getCourseName());
        assertEquals(testClass.getLecturer().getId(), classResponse.getLecturerId());
        assertEquals(testClass.getLecturer().getFullName(), classResponse.getLecturerName());

        verify(classRepository).findAll(pageable);
    }

    @Test
    void getAllClasses_WithEmptyResult_ShouldReturnEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Class> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(classRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<ClassResponse> result = classService.getAllClasses(pageable);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(classRepository).findAll(pageable);
    }

    @Test
    void getClassById_WithValidId_ShouldReturnClassResponse() {
        // Given
        Integer classId = 1;
        when(classRepository.findById(classId)).thenReturn(Optional.of(testClass));

        // When
        ClassResponse result = classService.getClassById(classId);

        // Then
        assertNotNull(result);
        assertEquals(testClass.getId(), result.getId());
        assertEquals(testClass.getClassCode(), result.getClassCode());
        assertEquals(testClass.getMaxStudents(), result.getMaxStudents());
        assertEquals(testClass.getSchedule(), result.getSchedule());
        assertEquals(testClass.getRoom(), result.getRoom());
        assertEquals(testClass.getCourse().getId(), result.getCourseId());
        assertEquals(testClass.getCourse().getCourseName(), result.getCourseName());
        assertEquals(testClass.getLecturer().getId(), result.getLecturerId());
        assertEquals(testClass.getLecturer().getFullName(), result.getLecturerName());

        verify(classRepository).findById(classId);
    }

    @Test
    void getClassById_WithInvalidId_ShouldThrowException() {
        // Given
        Integer invalidClassId = 999;
        when(classRepository.findById(invalidClassId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classService.getClassById(invalidClassId)
        );
        assertEquals("Class not found", exception.getMessage());

        verify(classRepository).findById(invalidClassId);
    }

    @Test
    void addClass_WithValidRequest_ShouldCreateAndReturnClass() {
        // Given
        when(courseRepository.findById(classRequest.getCourseId())).thenReturn(Optional.of(testCourse));
        when(lecturerRepository.findById(classRequest.getLecturerId())).thenReturn(Optional.of(testLecturer));
        when(semesterRepository.findById(classRequest.getSemesterId())).thenReturn(Optional.of(testSemester));
        when(classRepository.save(any(Class.class))).thenReturn(testClass);

        // When
        ClassResponse result = classService.addClass(classRequest);

        // Then
        assertNotNull(result);
        assertEquals(testClass.getClassCode(), result.getClassCode());
        assertEquals(testClass.getMaxStudents(), result.getMaxStudents());
        assertEquals(testClass.getSchedule(), result.getSchedule());
        assertEquals(testClass.getRoom(), result.getRoom());
        assertEquals(testClass.getCourse().getId(), result.getCourseId());
        assertEquals(testClass.getLecturer().getId(), result.getLecturerId());
        assertEquals(testClass.getSemester().getId(), result.getSemesterId());

        verify(courseRepository).findById(classRequest.getCourseId());
        verify(lecturerRepository).findById(classRequest.getLecturerId());
        verify(semesterRepository).findById(classRequest.getSemesterId());
        verify(classRepository).save(any(Class.class));
    }

    @Test
    void addClass_WithInvalidCourseId_ShouldThrowException() {
        // Given
        when(courseRepository.findById(classRequest.getCourseId())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classService.addClass(classRequest)
        );
        assertEquals("Course not found", exception.getMessage());

        verify(courseRepository).findById(classRequest.getCourseId());
        verify(lecturerRepository, never()).findById(anyInt());
        verify(semesterRepository, never()).findById(anyInt());
        verify(classRepository, never()).save(any(Class.class));
    }

    @Test
    void addClass_WithInactiveCourse_ShouldThrowException() {
        // Given
        Course inactiveCourse = Course.builder()
                .id(1)
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .isActive(false)
                .faculty(testFaculty)
                .build();

        when(courseRepository.findById(classRequest.getCourseId())).thenReturn(Optional.of(inactiveCourse));

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classService.addClass(classRequest)
        );
        assertEquals("Course is not active", exception.getMessage());

        verify(courseRepository).findById(classRequest.getCourseId());
        verify(lecturerRepository, never()).findById(anyInt());
        verify(semesterRepository, never()).findById(anyInt());
        verify(classRepository, never()).save(any(Class.class));
    }

    @Test
    void addClass_WithInvalidLecturerId_ShouldThrowException() {
        // Given
        when(courseRepository.findById(classRequest.getCourseId())).thenReturn(Optional.of(testCourse));
        when(lecturerRepository.findById(classRequest.getLecturerId())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classService.addClass(classRequest)
        );
        assertEquals("Lecturer not found", exception.getMessage());

        verify(courseRepository).findById(classRequest.getCourseId());
        verify(lecturerRepository).findById(classRequest.getLecturerId());
        verify(semesterRepository, never()).findById(anyInt());
        verify(classRepository, never()).save(any(Class.class));
    }

    @Test
    void addClass_WithInvalidSemesterId_ShouldThrowException() {
        // Given
        when(courseRepository.findById(classRequest.getCourseId())).thenReturn(Optional.of(testCourse));
        when(lecturerRepository.findById(classRequest.getLecturerId())).thenReturn(Optional.of(testLecturer));
        when(semesterRepository.findById(classRequest.getSemesterId())).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> classService.addClass(classRequest)
        );
        assertEquals("Semester not found", exception.getMessage());

        verify(courseRepository).findById(classRequest.getCourseId());
        verify(lecturerRepository).findById(classRequest.getLecturerId());
        verify(semesterRepository).findById(classRequest.getSemesterId());
        verify(classRepository, never()).save(any(Class.class));
    }

    @Test
    void addClass_WithCompleteValidData_ShouldSetAllRelationshipsCorrectly() {
        // Given
        Class capturedClass = Class.builder()
                .classCode(classRequest.getClassCode())
                .maxStudents(classRequest.getMaxStudents())
                .schedule(classRequest.getSchedule())
                .room(classRequest.getRoom())
                .build();

        when(courseRepository.findById(classRequest.getCourseId())).thenReturn(Optional.of(testCourse));
        when(lecturerRepository.findById(classRequest.getLecturerId())).thenReturn(Optional.of(testLecturer));
        when(semesterRepository.findById(classRequest.getSemesterId())).thenReturn(Optional.of(testSemester));
        when(classRepository.save(any(Class.class))).thenAnswer(invocation -> {
            Class savedClass = invocation.getArgument(0);
            savedClass.setId(1);
            return savedClass;
        });

        // When
        ClassResponse result = classService.addClass(classRequest);

        // Then
        assertNotNull(result);
        assertEquals(classRequest.getClassCode(), result.getClassCode());
        assertEquals(classRequest.getMaxStudents(), result.getMaxStudents());
        assertEquals(classRequest.getSchedule(), result.getSchedule());
        assertEquals(classRequest.getRoom(), result.getRoom());

        verify(classRepository).save(argThat(savedClass ->
                savedClass.getCourse().equals(testCourse) &&
                        savedClass.getLecturer().equals(testLecturer) &&
                        savedClass.getSemester().equals(testSemester)
        ));
    }
}