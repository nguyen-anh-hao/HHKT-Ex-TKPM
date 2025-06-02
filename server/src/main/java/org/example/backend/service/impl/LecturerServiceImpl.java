package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.LanguageInterceptor;
import org.example.backend.domain.Class;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Lecturer;
import org.example.backend.dto.request.LecturerRequest;
import org.example.backend.dto.response.LecturerResponse;
import org.example.backend.mapper.LecturerMapper;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.repository.ILecturerRepository;
import org.example.backend.service.ILecturerService;
import org.example.backend.service.ITranslationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LecturerServiceImpl implements ILecturerService {
    private final ILecturerRepository lecturerRepository;
    private final IFacultyRepository facultyRepository;
    private final ITranslationService translationService;

    @Override
    public Page<LecturerResponse> getAllLecturers(Pageable pageable) {
        log.info("Getting all lecturers");
        Page<Lecturer> lecturerPage = lecturerRepository.findAll(pageable);

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return lecturerPage.map(LecturerMapper::mapFromDomainToResponse);
        } else {
            // Get translations for faculties
            List<Integer> facultyIds = lecturerPage.getContent().stream()
                    .map(lecturer -> lecturer.getFaculty().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> facultyTranslations = translationService
                    .getTranslations("Faculty", facultyIds, currentLanguage);

            // Get translations for classes
            List<Integer> classIds = lecturerPage.getContent().stream()
                    .filter(lecturer -> lecturer.getClasses() != null && !lecturer.getClasses().isEmpty())
                    .flatMap(lecturer -> lecturer.getClasses().stream())
                    .map(Class::getId)
                    .distinct()
                    .toList();

            Map<Integer, Map<String, String>> classTranslations = classIds.isEmpty() ?
                    Collections.emptyMap() :
                    translationService.getTranslations("Class", classIds, currentLanguage);

            return lecturerPage.map(lecturer -> LecturerMapper.mapFromDomainToResponseWithTranslation(
                    lecturer,
                    facultyTranslations.getOrDefault(lecturer.getFaculty().getId(), Collections.emptyMap()),
                    classTranslations
            ));
        }
    }

    @Override
    public LecturerResponse getLecturerById(Integer lecturerId) {
        log.info("Getting lecturer by id: {}", lecturerId);
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return LecturerMapper.mapFromDomainToResponse(lecturer);
        } else {
            // Get translation for faculty
            Map<String, String> facultyTranslations = translationService.getTranslation(
                    "Faculty", lecturer.getFaculty().getId(), currentLanguage);

            // Get translations for classes if present
            Map<Integer, Map<String, String>> classTranslations = Collections.emptyMap();
            if (lecturer.getClasses() != null && !lecturer.getClasses().isEmpty()) {
                List<Integer> classIds = lecturer.getClasses().stream()
                        .map(Class::getId)
                        .toList();
                classTranslations = translationService
                        .getTranslations("Class", classIds, currentLanguage);
            }

            return LecturerMapper.mapFromDomainToResponseWithTranslation(
                    lecturer,
                    facultyTranslations,
                    classTranslations
            );
        }
    }

    @Override
    @Transactional
    public LecturerResponse addLecturer(LecturerRequest request) {
        log.info("Adding lecturer: {}", request.getFullName());

        if (lecturerRepository.existsByEmail(request.getEmail())) {
            log.error("Email already exists: {}", request.getEmail());
            throw new RuntimeException("Email already exists");
        }

        if (lecturerRepository.existsByPhone(request.getPhone())) {
            log.error("Phone already exists: {}", request.getPhone());
            throw new RuntimeException("Phone already exists");
        }

        log.info("Checking if faculty exists: {}", request.getFacultyId());
        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> {
                    log.error("Faculty not found");
                    return new RuntimeException("Faculty not found");
                });

        Lecturer lecturer = LecturerMapper.mapFromRequestToDomain(request);
        lecturer.setFaculty(faculty);

        lecturer = lecturerRepository.save(lecturer);

        log.info("Lecturer added successfully: {}", request.getFullName());

        return LecturerMapper.mapFromDomainToResponse(lecturer);
    }
}
