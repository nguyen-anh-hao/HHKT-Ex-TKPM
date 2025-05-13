package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.LanguageInterceptor;
import org.example.backend.domain.Student;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentStatusResponse;
import org.example.backend.mapper.StudentStatusMapper;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.IStudentStatusService;
import org.example.backend.service.ITranslationService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentStatusServiceImpl implements IStudentStatusService {
    private final IStudentStatusRepository studentStatusRepository;
    private final ITranslationService translationService;

    @Override
    public StudentStatusResponse addStudentStatus(StudentStatusRequest request) {
        if (studentStatusRepository.findByStudentStatusName(request.getStudentStatusName()).isPresent()) {
            log.error("Student status already exists");
            throw new RuntimeException("Student status already exists");
        }

        StudentStatus studentStatus = StudentStatusMapper.mapToDomain(request);
        studentStatus = studentStatusRepository.save(studentStatus);

        log.info("Student status saved to database successfully");

        return StudentStatusMapper.mapToResponse(studentStatus);
    }

    @Override
    public List<StudentStatusResponse> getAllStudentStatuses() {
        List<StudentStatus> studentStatuses = studentStatusRepository.findAll();
        log.info("Retrieved all student statuses from database");

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return studentStatuses.stream()
                    .map(StudentStatusMapper::mapToResponse)
                    .collect(Collectors.toList());
        } else {
            // Get translations for student statuses
            List<Integer> statusIds = studentStatuses.stream()
                    .map(StudentStatus::getId)
                    .toList();
            Map<Integer, Map<String, String>> statusTranslations = translationService
                    .getTranslations("StudentStatus", statusIds, currentLanguage);

            // If statuses have students, get translations for students and related entities
            List<Integer> studentIds = studentStatuses.stream()
                    .filter(status -> status.getStudents() != null && !status.getStudents().isEmpty())
                    .flatMap(status -> status.getStudents().stream())
                    .map(Student::getStudentId)
                    .map(studentId -> Integer.parseInt(studentId.replaceAll("[^\\d]", "")))
                    .distinct()
                    .toList();

            Map<Integer, Map<String, String>> studentTranslations = Collections.emptyMap();
            if (!studentIds.isEmpty()) {
                studentTranslations = translationService
                        .getTranslations("Student", studentIds, currentLanguage);
            }

            final Map<Integer, Map<String, String>> finalStudentTranslations = studentTranslations;

            return studentStatuses.stream()
                    .map(status -> StudentStatusMapper.mapToResponseWithTranslation(
                            status,
                            statusTranslations.getOrDefault(status.getId(), Collections.emptyMap()),
                            finalStudentTranslations
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public StudentStatusResponse getStudentStatusById(Integer studentStatusId) {
        StudentStatus studentStatus = studentStatusRepository.findById(studentStatusId)
                .orElseThrow(() -> {
                    log.error("Student status not found");
                    return new RuntimeException("Student status not found");
                });

        log.info("Retrieved student status from database");

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return StudentStatusMapper.mapToResponse(studentStatus);
        } else {
            // Get translation for student status
            Map<String, String> statusTranslations = translationService.getTranslation(
                    "StudentStatus", studentStatus.getId(), currentLanguage);

            // Get translations for students if present
            Map<Integer, Map<String, String>> studentTranslations = Collections.emptyMap();
            if (studentStatus.getStudents() != null && !studentStatus.getStudents().isEmpty()) {
                List<Integer> studentIds = studentStatus.getStudents().stream()
                        .map(Student::getStudentId)
                        .map(studentId -> Integer.parseInt(studentId.replaceAll("[^\\d]", "")))
                        .toList();
                studentTranslations = translationService
                        .getTranslations("Student", studentIds, currentLanguage);
            }

            return StudentStatusMapper.mapToResponseWithTranslation(
                    studentStatus,
                    statusTranslations,
                    studentTranslations
            );
        }
    }

    @Override
    public StudentStatusResponse updateStudentStatus(Integer studentStatusId, StudentStatusRequest request) {
        StudentStatus studentStatus = studentStatusRepository.findById(studentStatusId)
                .orElseThrow(() -> {
                    log.error("Student status not found");
                    return new RuntimeException("Student status not found");
                });

        studentStatus.setStudentStatusName(request.getStudentStatusName());
        studentStatus = studentStatusRepository.save(studentStatus);

        log.info("Student status updated successfully");

        return StudentStatusMapper.mapToResponse(studentStatus);
    }

    @Override
    public void deleteStudentStatus(Integer studentStatusId) {
        StudentStatus studentStatus = studentStatusRepository.findById(studentStatusId)
                .orElseThrow(() -> {
                    log.error("Student status not found");
                    return new RuntimeException("Student status not found");
                });

        studentStatusRepository.delete(studentStatus);

        log.info("Student status deleted successfully");
    }
}
