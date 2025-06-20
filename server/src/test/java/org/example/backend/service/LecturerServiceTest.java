package org.example.backend.service;

import org.example.backend.domain.Faculty;
import org.example.backend.domain.Lecturer;
import org.example.backend.dto.request.LecturerRequest;
import org.example.backend.dto.response.LecturerResponse;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.repository.ILecturerRepository;
import org.example.backend.service.impl.LecturerServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LecturerServiceTest {

    @Mock
    private ILecturerRepository lecturerRepository;

    @Mock
    private IFacultyRepository facultyRepository;

    @InjectMocks
    private LecturerServiceImpl lecturerService;

    private Lecturer testLecturer;
    private Faculty testFaculty;
    private LecturerRequest lecturerRequest;

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

        testLecturer = Lecturer.builder()
                .id(1)
                .fullName("Dr. John Smith")
                .email("john.smith@university.edu")
                .phone("0123456789")
                .faculty(testFaculty)
                .build();
        testLecturer.setCreatedAt(LocalDateTime.now());
        testLecturer.setUpdatedAt(LocalDateTime.now());
        testLecturer.setCreatedBy("admin");
        testLecturer.setUpdatedBy("admin");
    }

    private void setupRequests() {
        lecturerRequest = new LecturerRequest();
        lecturerRequest.setFullName("Dr. John Smith");
        lecturerRequest.setEmail("john.smith@university.edu");
        lecturerRequest.setPhone("0123456789");
        lecturerRequest.setFacultyId(1);
    }

    @Test
    void getAllLecturers_WithValidPageable_ShouldReturnPageOfLecturerResponses() {
        // Given
        Lecturer secondLecturer = Lecturer.builder()
                .id(2)
                .fullName("Dr. Jane Doe")
                .email("jane.doe@university.edu")
                .phone("0987654321")
                .faculty(testFaculty)
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Lecturer> lecturerPage = new PageImpl<>(Arrays.asList(testLecturer, secondLecturer), pageable, 2);
        when(lecturerRepository.findAll(pageable)).thenReturn(lecturerPage);

        // When
        Page<LecturerResponse> result = lecturerService.getAllLecturers(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());

        LecturerResponse firstLecturer = result.getContent().get(0);
        assertEquals(testLecturer.getId(), firstLecturer.getId());
        assertEquals(testLecturer.getFullName(), firstLecturer.getFullName());
        assertEquals(testLecturer.getEmail(), firstLecturer.getEmail());
        assertEquals(testLecturer.getPhone(), firstLecturer.getPhone());
        assertEquals(testLecturer.getFaculty().getId(), firstLecturer.getFacultyId());
        assertEquals(testLecturer.getFaculty().getFacultyName(), firstLecturer.getFacultyName());

        LecturerResponse secondLecturerResponse = result.getContent().get(1);
        assertEquals(secondLecturer.getId(), secondLecturerResponse.getId());
        assertEquals(secondLecturer.getFullName(), secondLecturerResponse.getFullName());
        assertEquals(secondLecturer.getEmail(), secondLecturerResponse.getEmail());

        verify(lecturerRepository).findAll(pageable);
    }

    @Test
    void getAllLecturers_WithEmptyResult_ShouldReturnEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Lecturer> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(lecturerRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<LecturerResponse> result = lecturerService.getAllLecturers(pageable);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(lecturerRepository).findAll(pageable);
    }

    @Test
    void getLecturerById_WithValidId_ShouldReturnLecturerResponse() {
        // Given
        Integer lecturerId = 1;
        when(lecturerRepository.findById(lecturerId)).thenReturn(Optional.of(testLecturer));

        // When
        LecturerResponse result = lecturerService.getLecturerById(lecturerId);

        // Then
        assertNotNull(result);
        assertEquals(testLecturer.getId(), result.getId());
        assertEquals(testLecturer.getFullName(), result.getFullName());
        assertEquals(testLecturer.getEmail(), result.getEmail());
        assertEquals(testLecturer.getPhone(), result.getPhone());
        assertEquals(testLecturer.getFaculty().getId(), result.getFacultyId());
        assertEquals(testLecturer.getFaculty().getFacultyName(), result.getFacultyName());
        assertEquals(testLecturer.getCreatedAt(), result.getCreatedAt());
        assertEquals(testLecturer.getUpdatedAt(), result.getUpdatedAt());
        assertEquals(testLecturer.getCreatedBy(), result.getCreatedBy());
        assertEquals(testLecturer.getUpdatedBy(), result.getUpdatedBy());

        verify(lecturerRepository).findById(lecturerId);
    }

    @Test
    void addLecturer_WithValidRequest_ShouldCreateAndReturnLecturer() {
        // Given
        when(lecturerRepository.existsByEmail(lecturerRequest.getEmail())).thenReturn(false);
        when(lecturerRepository.existsByPhone(lecturerRequest.getPhone())).thenReturn(false);
        when(facultyRepository.findById(lecturerRequest.getFacultyId())).thenReturn(Optional.of(testFaculty));
        when(lecturerRepository.save(any(Lecturer.class))).thenReturn(testLecturer);

        // When
        LecturerResponse result = lecturerService.addLecturer(lecturerRequest);

        // Then
        assertNotNull(result);
        assertEquals(testLecturer.getId(), result.getId());
        assertEquals(testLecturer.getFullName(), result.getFullName());
        assertEquals(testLecturer.getEmail(), result.getEmail());
        assertEquals(testLecturer.getPhone(), result.getPhone());
        assertEquals(testLecturer.getFaculty().getId(), result.getFacultyId());
        assertEquals(testLecturer.getFaculty().getFacultyName(), result.getFacultyName());

        verify(lecturerRepository).existsByEmail(lecturerRequest.getEmail());
        verify(lecturerRepository).existsByPhone(lecturerRequest.getPhone());
        verify(facultyRepository).findById(lecturerRequest.getFacultyId());
        verify(lecturerRepository).save(any(Lecturer.class));
    }

    @Test
    void addLecturer_WithDifferentFaculty_ShouldCreateLecturerWithCorrectFaculty() {
        // Given
        Faculty engineeringFaculty = Faculty.builder()
                .id(2)
                .facultyName("Engineering")
                .build();

        Lecturer engineeringLecturer = Lecturer.builder()
                .id(2)
                .fullName("Prof. Alice Johnson")
                .email("alice.johnson@university.edu")
                .phone("0111222333")
                .faculty(engineeringFaculty)
                .build();

        LecturerRequest engineeringRequest = new LecturerRequest();
        engineeringRequest.setFullName("Prof. Alice Johnson");
        engineeringRequest.setEmail("alice.johnson@university.edu");
        engineeringRequest.setPhone("0111222333");
        engineeringRequest.setFacultyId(2);

        when(lecturerRepository.existsByEmail(engineeringRequest.getEmail())).thenReturn(false);
        when(lecturerRepository.existsByPhone(engineeringRequest.getPhone())).thenReturn(false);
        when(facultyRepository.findById(engineeringRequest.getFacultyId())).thenReturn(Optional.of(engineeringFaculty));
        when(lecturerRepository.save(any(Lecturer.class))).thenReturn(engineeringLecturer);

        // When
        LecturerResponse result = lecturerService.addLecturer(engineeringRequest);

        // Then
        assertNotNull(result);
        assertEquals(engineeringLecturer.getId(), result.getId());
        assertEquals(engineeringLecturer.getFullName(), result.getFullName());
        assertEquals(engineeringLecturer.getEmail(), result.getEmail());
        assertEquals(engineeringLecturer.getPhone(), result.getPhone());
        assertEquals(engineeringFaculty.getId(), result.getFacultyId());
        assertEquals(engineeringFaculty.getFacultyName(), result.getFacultyName());

        verify(lecturerRepository).existsByEmail(engineeringRequest.getEmail());
        verify(lecturerRepository).existsByPhone(engineeringRequest.getPhone());
        verify(facultyRepository).findById(engineeringRequest.getFacultyId());
        verify(lecturerRepository).save(argThat(lecturer ->
                lecturer.getFaculty().equals(engineeringFaculty) &&
                        lecturer.getFullName().equals(engineeringRequest.getFullName()) &&
                        lecturer.getEmail().equals(engineeringRequest.getEmail()) &&
                        lecturer.getPhone().equals(engineeringRequest.getPhone())
        ));
    }

    @Test
    void addLecturer_WithUniqueEmailAndPhone_ShouldSuccessfullyCreate() {
        // Given
        LecturerRequest uniqueRequest = new LecturerRequest();
        uniqueRequest.setFullName("Dr. Michael Brown");
        uniqueRequest.setEmail("michael.brown@university.edu");
        uniqueRequest.setPhone("0555666777");
        uniqueRequest.setFacultyId(1);

        Lecturer uniqueLecturer = Lecturer.builder()
                .id(3)
                .fullName("Dr. Michael Brown")
                .email("michael.brown@university.edu")
                .phone("0555666777")
                .faculty(testFaculty)
                .build();

        when(lecturerRepository.existsByEmail(uniqueRequest.getEmail())).thenReturn(false);
        when(lecturerRepository.existsByPhone(uniqueRequest.getPhone())).thenReturn(false);
        when(facultyRepository.findById(uniqueRequest.getFacultyId())).thenReturn(Optional.of(testFaculty));
        when(lecturerRepository.save(any(Lecturer.class))).thenReturn(uniqueLecturer);

        // When
        LecturerResponse result = lecturerService.addLecturer(uniqueRequest);

        // Then
        assertNotNull(result);
        assertEquals(uniqueLecturer.getId(), result.getId());
        assertEquals(uniqueLecturer.getFullName(), result.getFullName());
        assertEquals(uniqueLecturer.getEmail(), result.getEmail());
        assertEquals(uniqueLecturer.getPhone(), result.getPhone());
        assertEquals(testFaculty.getId(), result.getFacultyId());
        assertEquals(testFaculty.getFacultyName(), result.getFacultyName());

        verify(lecturerRepository).existsByEmail(uniqueRequest.getEmail());
        verify(lecturerRepository).existsByPhone(uniqueRequest.getPhone());
        verify(facultyRepository).findById(uniqueRequest.getFacultyId());
        verify(lecturerRepository).save(any(Lecturer.class));
    }

    @Test
    void addLecturer_ShouldSetFacultyRelationshipCorrectly() {
        // Given
        when(lecturerRepository.existsByEmail(lecturerRequest.getEmail())).thenReturn(false);
        when(lecturerRepository.existsByPhone(lecturerRequest.getPhone())).thenReturn(false);
        when(facultyRepository.findById(lecturerRequest.getFacultyId())).thenReturn(Optional.of(testFaculty));
        when(lecturerRepository.save(any(Lecturer.class))).thenReturn(testLecturer);

        // When
        LecturerResponse result = lecturerService.addLecturer(lecturerRequest);

        // Then
        assertNotNull(result);
        assertEquals(testFaculty.getId(), result.getFacultyId());
        assertEquals(testFaculty.getFacultyName(), result.getFacultyName());

        verify(lecturerRepository).save(argThat(savedLecturer ->
                savedLecturer.getFaculty().equals(testFaculty) &&
                        savedLecturer.getFullName().equals(lecturerRequest.getFullName()) &&
                        savedLecturer.getEmail().equals(lecturerRequest.getEmail()) &&
                        savedLecturer.getPhone().equals(lecturerRequest.getPhone())
        ));
    }
}