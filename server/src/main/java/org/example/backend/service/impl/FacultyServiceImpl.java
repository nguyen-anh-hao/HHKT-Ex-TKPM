package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.LanguageInterceptor;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Student;
import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.FacultyResponse;
import org.example.backend.mapper.FacultyMapper;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.service.IFacultyService;
import org.example.backend.service.ITranslationService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacultyServiceImpl implements IFacultyService {
    private final IFacultyRepository facultyRepository;
    private final ITranslationService translationService;

    @Override
    @Transactional
    public FacultyResponse addFaculty(FacultyRequest request) {
        if (facultyRepository.findByFacultyName(request.getFacultyName()).isPresent()) {
            log.error("Faculty already exists");
            throw new RuntimeException("Faculty already exists");
        }

        Faculty faculty = FacultyMapper.mapToDomain(request);
        faculty = facultyRepository.save(faculty);

        log.info("Faculty saved to database successfully");

        return FacultyMapper.mapToResponse(faculty);
    }

    @Override
    public List<FacultyResponse> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        log.info("Retrieved all faculties from database");

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return faculties.stream()
                    .map(FacultyMapper::mapToResponse)
                    .collect(Collectors.toList());
        } else {
            // Get translations for faculties
            List<Integer> facultyIds = faculties.stream()
                    .map(Faculty::getId)
                    .toList();
            Map<Integer, Map<String, String>> facultyTranslations = translationService
                    .getTranslations("Faculty", facultyIds, currentLanguage);

            // If faculties have students, get translations for students
            List<Integer> studentIds = faculties.stream()
                    .filter(faculty -> faculty.getStudents() != null && !faculty.getStudents().isEmpty())
                    .flatMap(faculty -> faculty.getStudents().stream())
                    .map(Student::getStudentId)
                    .map(id -> Integer.parseInt(id.replaceAll("[^\\d]", "")))
                    .distinct()
                    .toList();

            Map<Integer, Map<String, String>> studentTranslations = Collections.emptyMap();
            if (!studentIds.isEmpty()) {
                studentTranslations = translationService
                        .getTranslations("Student", studentIds, currentLanguage);
            }

            final Map<Integer, Map<String, String>> finalStudentTranslations = studentTranslations;

            return faculties.stream()
                    .map(faculty -> FacultyMapper.mapToResponseWithTranslation(
                            faculty,
                            facultyTranslations.getOrDefault(faculty.getId(), Collections.emptyMap()),
                            finalStudentTranslations
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public FacultyResponse getFacultyById(Integer id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Faculty not found");
                    return new RuntimeException("Faculty not found");
                });

        log.info("Retrieved faculty from database");

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return FacultyMapper.mapToResponse(faculty);
        } else {
            // Get translation for faculty
            Map<String, String> facultyTranslations = translationService.getTranslation(
                    "Faculty", faculty.getId(), currentLanguage);

            // Get translations for students if present
            Map<Integer, Map<String, String>> studentTranslations = Collections.emptyMap();
            if (faculty.getStudents() != null && !faculty.getStudents().isEmpty()) {
                List<Integer> studentIds = faculty.getStudents().stream()
                        .map(Student::getStudentId)
                        .map(studentId -> Integer.parseInt(studentId.replaceAll("[^\\d]", "")))
                        .toList();
                studentTranslations = translationService
                        .getTranslations("Student", studentIds, currentLanguage);
            }

            return FacultyMapper.mapToResponseWithTranslation(
                    faculty,
                    facultyTranslations,
                    studentTranslations
            );
        }
    }

    @Override
    @Transactional
    public FacultyResponse updateFaculty(Integer id, FacultyRequest request) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Faculty not found");
                    return new RuntimeException("Faculty not found");
                });

        faculty.setFacultyName(request.getFacultyName());

        faculty = facultyRepository.save(faculty);

        log.info("Faculty updated successfully");

        return FacultyMapper.mapToResponse(faculty);
    }

    @Override
    @Transactional
    public void deleteFaculty(Integer id) {
       Faculty faculty = facultyRepository.findById(id)
               .orElseThrow(() -> {
                   log.error("Faculty not found");
                   return new RuntimeException("Faculty not found");
               });

        facultyRepository.delete(faculty);

        log.info("Faculty deleted successfully");
    }
}
