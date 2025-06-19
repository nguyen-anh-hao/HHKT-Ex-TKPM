package org.example.backend.service;

import org.example.backend.domain.Semester;
import org.example.backend.dto.request.SemesterRequest;
import org.example.backend.dto.response.SemesterResponse;
import org.example.backend.repository.ISemesterRepository;
import org.example.backend.service.impl.SemesterServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SemesterServiceTest {

    @Mock
    private ISemesterRepository semesterRepository;

    @InjectMocks
    private SemesterServiceImpl semesterService;

    private Semester testSemester;
    private SemesterRequest semesterRequest;

    @BeforeEach
    void setUp() {
        setupTestData();
        setupRequests();
    }

    private void setupTestData() {
        testSemester = Semester.builder()
                .id(1)
                .semester(1)
                .startDate(LocalDate.of(2024, 1, 15))
                .endDate(LocalDate.of(2024, 5, 15))
                .academicYear("2024-2024")
                .lastCancelDate(LocalDate.of(2024, 3, 1))
                .classes(Collections.emptyList())
                .build();
        testSemester.setCreatedAt(LocalDateTime.now());
        testSemester.setUpdatedAt(LocalDateTime.now());
        testSemester.setCreatedBy("admin");
        testSemester.setUpdatedBy("admin");
    }

    private void setupRequests() {
        semesterRequest = new SemesterRequest();
        semesterRequest.setSemester(1);
        semesterRequest.setStartDate(LocalDate.of(2024, 1, 15));
        semesterRequest.setEndDate(LocalDate.of(2024, 5, 15));
        semesterRequest.setAcademicYear("2024-2024");
        semesterRequest.setLastCancelDate(LocalDate.of(2024, 3, 1));
    }

    @Test
    void getAllSemesters_WithValidPageable_ShouldReturnPageOfSemesterResponses() {
        // Given
        Semester secondSemester = Semester.builder()
                .id(2)
                .semester(2)
                .startDate(LocalDate.of(2024, 6, 1))
                .endDate(LocalDate.of(2024, 10, 15))
                .academicYear("2024-2024")
                .lastCancelDate(LocalDate.of(2024, 8, 1))
                .classes(Collections.emptyList())
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Semester> semesterPage = new PageImpl<>(Arrays.asList(testSemester, secondSemester), pageable, 2);
        when(semesterRepository.findAll(pageable)).thenReturn(semesterPage);

        // When
        Page<SemesterResponse> result = semesterService.getAllSemesters(pageable);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());

        SemesterResponse firstSemester = result.getContent().get(0);
        assertEquals(testSemester.getId(), firstSemester.getId());
        assertEquals(testSemester.getSemester(), firstSemester.getSemester());
        assertEquals(testSemester.getStartDate(), firstSemester.getStartDate());
        assertEquals(testSemester.getEndDate(), firstSemester.getEndDate());
        assertEquals(testSemester.getAcademicYear(), firstSemester.getAcademicYear());
        assertEquals(testSemester.getLastCancelDate(), firstSemester.getLastCancelDate());

        SemesterResponse secondSemesterResponse = result.getContent().get(1);
        assertEquals(secondSemester.getId(), secondSemesterResponse.getId());
        assertEquals(secondSemester.getSemester(), secondSemesterResponse.getSemester());
        assertEquals(secondSemester.getAcademicYear(), secondSemesterResponse.getAcademicYear());

        verify(semesterRepository).findAll(pageable);
    }

    @Test
    void getAllSemesters_WithEmptyResult_ShouldReturnEmptyPage() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Semester> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(semesterRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<SemesterResponse> result = semesterService.getAllSemesters(pageable);

        // Then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(semesterRepository).findAll(pageable);
    }

    @Test
    void getSemesterById_WithValidId_ShouldReturnSemesterResponse() {
        // Given
        Integer semesterId = 1;
        when(semesterRepository.findById(semesterId)).thenReturn(Optional.of(testSemester));

        // When
        SemesterResponse result = semesterService.getSemesterById(semesterId);

        // Then
        assertNotNull(result);
        assertEquals(testSemester.getId(), result.getId());
        assertEquals(testSemester.getSemester(), result.getSemester());
        assertEquals(testSemester.getStartDate(), result.getStartDate());
        assertEquals(testSemester.getEndDate(), result.getEndDate());
        assertEquals(testSemester.getAcademicYear(), result.getAcademicYear());
        assertEquals(testSemester.getLastCancelDate(), result.getLastCancelDate());
        assertEquals(testSemester.getCreatedAt(), result.getCreatedAt());
        assertEquals(testSemester.getUpdatedAt(), result.getUpdatedAt());
        assertEquals(testSemester.getCreatedBy(), result.getCreatedBy());
        assertEquals(testSemester.getUpdatedBy(), result.getUpdatedBy());

        verify(semesterRepository).findById(semesterId);
    }

    @Test
    void addSemester_WithValidRequest_ShouldCreateAndReturnSemester() {
        // Given
        when(semesterRepository.findBySemesterAndAcademicYear(
                semesterRequest.getSemester(),
                semesterRequest.getAcademicYear()
        )).thenReturn(Optional.empty());
        when(semesterRepository.save(any(Semester.class))).thenReturn(testSemester);

        // When
        SemesterResponse result = semesterService.addSemester(semesterRequest);

        // Then
        assertNotNull(result);
        assertEquals(testSemester.getId(), result.getId());
        assertEquals(testSemester.getSemester(), result.getSemester());
        assertEquals(testSemester.getStartDate(), result.getStartDate());
        assertEquals(testSemester.getEndDate(), result.getEndDate());
        assertEquals(testSemester.getAcademicYear(), result.getAcademicYear());
        assertEquals(testSemester.getLastCancelDate(), result.getLastCancelDate());

        verify(semesterRepository).findBySemesterAndAcademicYear(
                semesterRequest.getSemester(),
                semesterRequest.getAcademicYear()
        );
        verify(semesterRepository).save(any(Semester.class));
    }

    @Test
    void addSemester_WithSecondSemester_ShouldCreateSuccessfully() {
        // Given
        SemesterRequest secondSemesterRequest = new SemesterRequest();
        secondSemesterRequest.setSemester(2);
        secondSemesterRequest.setStartDate(LocalDate.of(2024, 6, 1));
        secondSemesterRequest.setEndDate(LocalDate.of(2024, 10, 15));
        secondSemesterRequest.setAcademicYear("2024-2024");
        secondSemesterRequest.setLastCancelDate(LocalDate.of(2024, 8, 1));

        Semester secondSemester = Semester.builder()
                .id(2)
                .semester(2)
                .startDate(LocalDate.of(2024, 6, 1))
                .endDate(LocalDate.of(2024, 10, 15))
                .academicYear("2024-2024")
                .lastCancelDate(LocalDate.of(2024, 8, 1))
                .build();

        when(semesterRepository.findBySemesterAndAcademicYear(
                secondSemesterRequest.getSemester(),
                secondSemesterRequest.getAcademicYear()
        )).thenReturn(Optional.empty());
        when(semesterRepository.save(any(Semester.class))).thenReturn(secondSemester);

        // When
        SemesterResponse result = semesterService.addSemester(secondSemesterRequest);

        // Then
        assertNotNull(result);
        assertEquals(secondSemester.getId(), result.getId());
        assertEquals(secondSemester.getSemester(), result.getSemester());
        assertEquals(secondSemester.getStartDate(), result.getStartDate());
        assertEquals(secondSemester.getEndDate(), result.getEndDate());
        assertEquals(secondSemester.getAcademicYear(), result.getAcademicYear());
        assertEquals(secondSemester.getLastCancelDate(), result.getLastCancelDate());

        verify(semesterRepository).findBySemesterAndAcademicYear(
                secondSemesterRequest.getSemester(),
                secondSemesterRequest.getAcademicYear()
        );
        verify(semesterRepository).save(any(Semester.class));
    }

    @Test
    void addSemester_WithThirdSemester_ShouldCreateSuccessfully() {
        // Given
        SemesterRequest thirdSemesterRequest = new SemesterRequest();
        thirdSemesterRequest.setSemester(3);
        thirdSemesterRequest.setStartDate(LocalDate.of(2024, 11, 1));
        thirdSemesterRequest.setEndDate(LocalDate.of(2025, 2, 28));
        thirdSemesterRequest.setAcademicYear("2024-2025");
        thirdSemesterRequest.setLastCancelDate(LocalDate.of(2024, 12, 15));

        Semester thirdSemester = Semester.builder()
                .id(3)
                .semester(3)
                .startDate(LocalDate.of(2024, 11, 1))
                .endDate(LocalDate.of(2025, 2, 28))
                .academicYear("2024-2025")
                .lastCancelDate(LocalDate.of(2024, 12, 15))
                .build();

        when(semesterRepository.findBySemesterAndAcademicYear(
                thirdSemesterRequest.getSemester(),
                thirdSemesterRequest.getAcademicYear()
        )).thenReturn(Optional.empty());
        when(semesterRepository.save(any(Semester.class))).thenReturn(thirdSemester);

        // When
        SemesterResponse result = semesterService.addSemester(thirdSemesterRequest);

        // Then
        assertNotNull(result);
        assertEquals(thirdSemester.getId(), result.getId());
        assertEquals(thirdSemester.getSemester(), result.getSemester());
        assertEquals(thirdSemester.getStartDate(), result.getStartDate());
        assertEquals(thirdSemester.getEndDate(), result.getEndDate());
        assertEquals(thirdSemester.getAcademicYear(), result.getAcademicYear());
        assertEquals(thirdSemester.getLastCancelDate(), result.getLastCancelDate());

        verify(semesterRepository).findBySemesterAndAcademicYear(
                thirdSemesterRequest.getSemester(),
                thirdSemesterRequest.getAcademicYear()
        );
        verify(semesterRepository).save(any(Semester.class));
    }

    @Test
    void addSemester_WithCrossYearAcademicYear_ShouldCreateSuccessfully() {
        // Given
        SemesterRequest crossYearRequest = new SemesterRequest();
        crossYearRequest.setSemester(1);
        crossYearRequest.setStartDate(LocalDate.of(2024, 9, 1));
        crossYearRequest.setEndDate(LocalDate.of(2025, 1, 15));
        crossYearRequest.setAcademicYear("2024-2025");
        crossYearRequest.setLastCancelDate(LocalDate.of(2024, 11, 1));

        Semester crossYearSemester = Semester.builder()
                .id(4)
                .semester(1)
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2025, 1, 15))
                .academicYear("2024-2025")
                .lastCancelDate(LocalDate.of(2024, 11, 1))
                .build();

        when(semesterRepository.findBySemesterAndAcademicYear(
                crossYearRequest.getSemester(),
                crossYearRequest.getAcademicYear()
        )).thenReturn(Optional.empty());
        when(semesterRepository.save(any(Semester.class))).thenReturn(crossYearSemester);

        // When
        SemesterResponse result = semesterService.addSemester(crossYearRequest);

        // Then
        assertNotNull(result);
        assertEquals(crossYearSemester.getId(), result.getId());
        assertEquals(crossYearSemester.getSemester(), result.getSemester());
        assertEquals(crossYearSemester.getStartDate(), result.getStartDate());
        assertEquals(crossYearSemester.getEndDate(), result.getEndDate());
        assertEquals(crossYearSemester.getAcademicYear(), result.getAcademicYear());
        assertEquals(crossYearSemester.getLastCancelDate(), result.getLastCancelDate());

        verify(semesterRepository).findBySemesterAndAcademicYear(
                crossYearRequest.getSemester(),
                crossYearRequest.getAcademicYear()
        );
        verify(semesterRepository).save(any(Semester.class));
    }

    @Test
    void addSemester_ShouldMapAllFieldsCorrectly() {
        // Given
        when(semesterRepository.findBySemesterAndAcademicYear(
                semesterRequest.getSemester(),
                semesterRequest.getAcademicYear()
        )).thenReturn(Optional.empty());
        when(semesterRepository.save(any(Semester.class))).thenReturn(testSemester);

        // When
        SemesterResponse result = semesterService.addSemester(semesterRequest);

        // Then
        verify(semesterRepository).save(argThat(savedSemester ->
                savedSemester.getSemester().equals(semesterRequest.getSemester()) &&
                        savedSemester.getStartDate().equals(semesterRequest.getStartDate()) &&
                        savedSemester.getEndDate().equals(semesterRequest.getEndDate()) &&
                        savedSemester.getAcademicYear().equals(semesterRequest.getAcademicYear()) &&
                        savedSemester.getLastCancelDate().equals(semesterRequest.getLastCancelDate())
        ));
    }
}