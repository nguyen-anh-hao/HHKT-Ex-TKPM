package org.example.backend.service;

import org.example.backend.domain.Course;
import org.example.backend.domain.Faculty;
import org.example.backend.dto.request.CourseRequest;
import org.example.backend.dto.request.CourseUpdateRequest;
import org.example.backend.dto.response.CourseResponse;
import org.example.backend.repository.IClassRegistrationRepository;
import org.example.backend.repository.IClassRepository;
import org.example.backend.repository.ICourseRepository;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.service.impl.CourseServiceImpl;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IFacultyRepository facultyRepository;

    @Mock
    private IClassRepository classRepository;

    @Mock
    private IClassRegistrationRepository classRegistrationRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course testCourse;
    private Course prerequisiteCourse;
    private Faculty testFaculty;
    private CourseRequest courseRequest;
    private CourseUpdateRequest courseUpdateRequest;

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

        prerequisiteCourse = Course.builder()
                .id(1)
                .courseCode("MATH101")
                .courseName("Mathematics")
                .credits(3)
                .description("Basic mathematics")
                .isActive(true)
                .faculty(testFaculty)
                .build();

        testCourse = Course.builder()
                .id(2)
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .description("Basic computer science course")
                .isActive(true)
                .faculty(testFaculty)
                .prerequisiteCourse(prerequisiteCourse)
                .build();
        testCourse.setCreatedAt(LocalDateTime.now().minusMinutes(15)); // Created 15 minutes ago
        testCourse.setUpdatedAt(LocalDateTime.now());
        testCourse.setCreatedBy("admin");
        testCourse.setUpdatedBy("admin");
    }

    private void setupRequests() {
        courseRequest = new CourseRequest();
        courseRequest.setCourseCode("CS101");
        courseRequest.setCourseName("Introduction to Computer Science");
        courseRequest.setCredits(3);
        courseRequest.setDescription("Basic computer science course");
        courseRequest.setIsActive(true);
        courseRequest.setFacultyId(1);
        courseRequest.setPrerequisiteCourseId(1);

        courseUpdateRequest = new CourseUpdateRequest();
        courseUpdateRequest.setCourseName("Advanced Computer Science");
        courseUpdateRequest.setCredits(4);
        courseUpdateRequest.setDescription("Advanced computer science course");
        courseUpdateRequest.setFacultyId(1);
    }

    @Test
    void addCourse_WithValidRequestAndPrerequisite_ShouldCreateCourse() {
        // Given
        when(courseRepository.existsByCourseName(courseRequest.getCourseName())).thenReturn(false);
        when(facultyRepository.findById(courseRequest.getFacultyId())).thenReturn(Optional.of(testFaculty));
        when(courseRepository.findById(courseRequest.getPrerequisiteCourseId())).thenReturn(Optional.of(prerequisiteCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // When
        CourseResponse result = courseService.addCourse(courseRequest);

        // Then
        assertNotNull(result);
        assertEquals(testCourse.getCourseCode(), result.getCourseCode());
        assertEquals(testCourse.getCourseName(), result.getCourseName());
        assertEquals(testCourse.getCredits(), result.getCredits());
        assertEquals(testCourse.getDescription(), result.getDescription());
        assertEquals(testCourse.getIsActive(), result.getIsActive());
        assertEquals(testCourse.getFaculty().getId(), result.getFacultyId());
        assertEquals(testCourse.getFaculty().getFacultyName(), result.getFacultyName());
        assertEquals(testCourse.getPrerequisiteCourse().getId(), result.getPrerequisiteCourseId());
        assertEquals(testCourse.getPrerequisiteCourse().getCourseName(), result.getPrerequisiteCourseName());

        verify(courseRepository).existsByCourseName(courseRequest.getCourseName());
        verify(facultyRepository).findById(courseRequest.getFacultyId());
        verify(courseRepository, times(2)).findById(courseRequest.getPrerequisiteCourseId());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void addCourse_WithValidRequestWithoutPrerequisite_ShouldCreateCourse() {
        // Given
        courseRequest.setPrerequisiteCourseId(null);
        Course courseWithoutPrerequisite = Course.builder()
                .id(2)
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .description("Basic computer science course")
                .isActive(true)
                .faculty(testFaculty)
                .prerequisiteCourse(null)
                .build();

        when(courseRepository.existsByCourseName(courseRequest.getCourseName())).thenReturn(false);
        when(facultyRepository.findById(courseRequest.getFacultyId())).thenReturn(Optional.of(testFaculty));
        when(courseRepository.save(any(Course.class))).thenReturn(courseWithoutPrerequisite);

        // When
        CourseResponse result = courseService.addCourse(courseRequest);

        // Then
        assertNotNull(result);
        assertEquals(courseWithoutPrerequisite.getCourseCode(), result.getCourseCode());
        assertEquals(courseWithoutPrerequisite.getCourseName(), result.getCourseName());
        assertEquals(courseWithoutPrerequisite.getCredits(), result.getCredits());
        assertEquals(courseWithoutPrerequisite.getFaculty().getId(), result.getFacultyId());
        assertNull(result.getPrerequisiteCourseId());

        verify(courseRepository).existsByCourseName(courseRequest.getCourseName());
        verify(facultyRepository).findById(courseRequest.getFacultyId());
        verify(courseRepository, never()).findById(anyInt());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void getCourseById_WithValidId_ShouldReturnCourseResponse() {
        // Given
        Integer courseId = 2;
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));

        // When
        CourseResponse result = courseService.getCourseById(courseId);

        // Then
        assertNotNull(result);
        assertEquals(testCourse.getId(), result.getCourseId());
        assertEquals(testCourse.getCourseCode(), result.getCourseCode());
        assertEquals(testCourse.getCourseName(), result.getCourseName());
        assertEquals(testCourse.getCredits(), result.getCredits());
        assertEquals(testCourse.getDescription(), result.getDescription());
        assertEquals(testCourse.getIsActive(), result.getIsActive());
        assertEquals(testCourse.getFaculty().getId(), result.getFacultyId());
        assertEquals(testCourse.getFaculty().getFacultyName(), result.getFacultyName());
        assertEquals(testCourse.getPrerequisiteCourse().getId(), result.getPrerequisiteCourseId());

        verify(courseRepository).findById(courseId);
    }

    @Test
    void getAllCourses_WithValidPageable_ShouldReturnPageOfCourseResponses() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Course> coursePage = new PageImpl<>(Arrays.asList(testCourse, prerequisiteCourse), pageable, 2);
        when(courseRepository.findAll(pageable)).thenReturn(coursePage);

        // When
        Page<CourseResponse> result = courseService.getAllCourses(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());

        CourseResponse firstCourse = result.getContent().get(0);
        assertEquals(testCourse.getId(), firstCourse.getCourseId());
        assertEquals(testCourse.getCourseName(), firstCourse.getCourseName());
        assertEquals(testCourse.getCredits(), firstCourse.getCredits());

        CourseResponse secondCourse = result.getContent().get(1);
        assertEquals(prerequisiteCourse.getId(), secondCourse.getCourseId());
        assertEquals(prerequisiteCourse.getCourseName(), secondCourse.getCourseName());

        verify(courseRepository).findAll(pageable);
    }

    @Test
    void updateCourse_WithValidRequestAndNoActiveRegistrations_ShouldUpdateCourse() {
        // Given
        Integer courseId = 2;
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));
        when(classRepository.findAllByCourseId(courseId)).thenReturn(Optional.of(Collections.emptyList()));
        when(facultyRepository.findById(courseUpdateRequest.getFacultyId())).thenReturn(Optional.of(testFaculty));
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // When
        CourseResponse result = courseService.updateCourse(courseId, courseUpdateRequest);

        // Then
        assertNotNull(result);
        assertEquals(testCourse.getId(), result.getCourseId());
        assertEquals(testCourse.getCourseName(), result.getCourseName());
        assertEquals(testCourse.getCredits(), result.getCredits());
        assertEquals(testCourse.getDescription(), result.getDescription());
        assertEquals(testCourse.getFaculty().getId(), result.getFacultyId());

        verify(courseRepository).findById(courseId);
        verify(classRepository).findAllByCourseId(courseId);
        verify(facultyRepository).findById(courseUpdateRequest.getFacultyId());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void updateCourse_WithOnlyNameAndDescription_ShouldUpdateSpecificFields() {
        // Given
        Integer courseId = 2;
        CourseUpdateRequest partialUpdateRequest = new CourseUpdateRequest();
        partialUpdateRequest.setCourseName("Updated Course Name");
        partialUpdateRequest.setDescription("Updated description");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(testCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(testCourse);

        // When
        CourseResponse result = courseService.updateCourse(courseId, partialUpdateRequest);

        // Then
        assertNotNull(result);
        assertEquals(testCourse.getId(), result.getCourseId());
        assertEquals(testCourse.getCourseName(), result.getCourseName());
        assertEquals(testCourse.getDescription(), result.getDescription());

        verify(courseRepository).findById(courseId);
        verify(classRepository, never()).findAllByCourseId(anyInt());
        verify(facultyRepository, never()).findById(anyInt());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void deleteCourse_WithRecentCourseAndNoClasses_ShouldDeleteCourse() {
        // Given
        Integer courseId = 2;
        Course recentCourse = Course.builder()
                .id(courseId)
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .isActive(true)
                .faculty(testFaculty)
                .build();
        recentCourse.setCreatedAt(LocalDateTime.now().minusMinutes(10)); // Created 10 minutes ago

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(recentCourse));
        when(classRepository.existsByCourseId(courseId)).thenReturn(false);

        // When
        CourseResponse result = courseService.deleteCourse(courseId);

        // Then
        assertNotNull(result);
        assertEquals(recentCourse.getId(), result.getCourseId());
        assertEquals(recentCourse.getCourseName(), result.getCourseName());

        verify(courseRepository).findById(courseId);
        verify(classRepository).existsByCourseId(courseId);
        verify(courseRepository).delete(recentCourse);
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void deleteCourse_WithRecentCourseButHasClasses_ShouldDeactivateCourse() {
        // Given
        Integer courseId = 2;
        Course recentCourse = Course.builder()
                .id(courseId)
                .courseCode("CS101")
                .courseName("Introduction to Computer Science")
                .credits(3)
                .isActive(true)
                .faculty(testFaculty)
                .build();
        recentCourse.setCreatedAt(LocalDateTime.now().minusMinutes(10)); // Created 10 minutes ago

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(recentCourse));
        when(classRepository.existsByCourseId(courseId)).thenReturn(true);
        when(courseRepository.save(any(Course.class))).thenReturn(recentCourse);

        // When
        CourseResponse result = courseService.deleteCourse(courseId);

        // Then
        assertNotNull(result);
        assertEquals(recentCourse.getId(), result.getCourseId());
        assertEquals(recentCourse.getCourseName(), result.getCourseName());

        verify(courseRepository).findById(courseId);
        verify(classRepository).existsByCourseId(courseId);
        verify(courseRepository).save(any(Course.class));
        verify(courseRepository, never()).delete(any(Course.class));
    }
}