package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.LanguageInterceptor;
import org.example.backend.domain.Class;
import org.example.backend.domain.Course;
import org.example.backend.domain.Lecturer;
import org.example.backend.domain.Semester;
import org.example.backend.dto.request.ClassRequest;
import org.example.backend.dto.response.ClassResponse;
import org.example.backend.mapper.ClassMapper;
import org.example.backend.repository.IClassRepository;
import org.example.backend.repository.ICourseRepository;
import org.example.backend.repository.ILecturerRepository;
import org.example.backend.repository.ISemesterRepository;
import org.example.backend.service.IClassService;
import org.example.backend.service.ITranslationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassServiceImpl implements IClassService {
    private final IClassRepository classRepository;
    private final ICourseRepository courseRepository;
    private final ILecturerRepository lecturerRepository;
    private final ISemesterRepository semesterRepository;
    private final ITranslationService translationService;

    @Override
    public ClassResponse getClassById(Integer classId) {
        log.info("Fetching class by id: {}", classId);
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return ClassMapper.mapFromDomainToClassResponse(classEntity);
        } else {
            // Get translation for class
            Map<String, String> classTranslations = translationService.getTranslation("Class", classId, currentLanguage);

            // Get translation for course
            Map<String, String> courseTranslations = translationService.getTranslation(
                    "Course", classEntity.getCourse().getId(), currentLanguage);

            // Get translation for faculty
            Map<String, String> facultyTranslations = translationService.getTranslation(
                    "Faculty", classEntity.getCourse().getFaculty().getId(), currentLanguage);

            // Get translation for prerequisite course
            Map<String, String> prerequisiteCourseTranslations = new HashMap<>();
            if (classEntity.getCourse().getPrerequisiteCourse() != null) {
                prerequisiteCourseTranslations = translationService.getTranslation(
                        "Course", classEntity.getCourse().getPrerequisiteCourse().getId(), currentLanguage);
            }

            return ClassMapper.mapFromDomainToClassResponseWithTranslation(
                    classEntity, classTranslations, courseTranslations, facultyTranslations,
                    prerequisiteCourseTranslations);
        }
    }

    @Override
    public Page<ClassResponse> getAllClasses(Pageable pageable) {
        log.info("Fetching all classes");
        Page<Class> classes = classRepository.findAll(pageable);

        String currentLanguage = LanguageInterceptor.CURRENT_LANGUAGE.get();
        if ("vi".equals(currentLanguage)) {
            return classes.map(ClassMapper::mapFromDomainToClassResponse);
        } else {
            // Get translations for classes
            List<Integer> classIds = classes.getContent().stream()
                    .map(Class::getId)
                    .toList();
            Map<Integer, Map<String, String>> classTranslations = translationService
                    .getTranslations("Class", classIds, currentLanguage);

            // Get translations for courses
            List<Integer> courseIds = classes.getContent().stream()
                    .map(classEntity -> classEntity.getCourse().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> courseTranslations = translationService
                    .getTranslations("Course", courseIds, currentLanguage);

            // Get translations for faculties
            List<Integer> facultyIds = classes.getContent().stream()
                    .map(classEntity -> classEntity.getCourse().getFaculty().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> facultyTranslations = translationService
                    .getTranslations("Faculty", facultyIds, currentLanguage);

            // Get translations for prerequisite courses
            List<Integer> prerequisiteCourseIds = classes.getContent().stream()
                    .filter(classEntity -> classEntity.getCourse().getPrerequisiteCourse() != null)
                    .map(classEntity -> classEntity.getCourse().getPrerequisiteCourse().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> prerequisiteCourseTranslations =
                    prerequisiteCourseIds.isEmpty() ? Collections.emptyMap() :
                            translationService.getTranslations("Course", prerequisiteCourseIds, currentLanguage);

            // Get translations for semesters
            List<Integer> semesterIds = classes.getContent().stream()
                    .map(classEntity -> classEntity.getSemester().getId())
                    .distinct()
                    .toList();
            Map<Integer, Map<String, String>> semesterTranslations = translationService
                    .getTranslations("Semester", semesterIds, currentLanguage);

            return classes.map(classEntity -> ClassMapper.mapFromDomainToClassResponseWithTranslation(
                    classEntity,
                    classTranslations.getOrDefault(classEntity.getId(), Collections.emptyMap()),
                    courseTranslations.getOrDefault(classEntity.getCourse().getId(), Collections.emptyMap()),
                    facultyTranslations.getOrDefault(classEntity.getCourse().getFaculty().getId(), Collections.emptyMap()),
                    classEntity.getCourse().getPrerequisiteCourse() != null ?
                            prerequisiteCourseTranslations.getOrDefault(classEntity.getCourse().getPrerequisiteCourse().getId(), Collections.emptyMap()) :
                            Collections.emptyMap()
            ));
        }
    }

    @Override
    @Transactional
    public ClassResponse addClass(ClassRequest classRequest) {
        log.info("Adding class: {}", classRequest.getClassCode());

        Class classEntity = ClassMapper.mapFromClassRequestToDomain(classRequest);

        log.info("Checking if course exists: {}", classRequest.getCourseId());
        Course course = courseRepository.findById(classRequest.getCourseId())
                .orElseThrow(() -> {
                    log.error("Course not found: {}", classRequest.getCourseId());
                    return new RuntimeException("Course not found");
                });

        log.info("Checking if course is active: {}", classRequest.getCourseId());
        if (!course.getIsActive()) {
            log.error("Course is not active: {}", classRequest.getCourseId());
            throw new RuntimeException("Course is not active");
        }

        log.info("Checking if lecturer exists: {}", classRequest.getLecturerId());
        Lecturer lecturer = lecturerRepository.findById(classRequest.getLecturerId())
                .orElseThrow(() -> {
                    log.error("Lecturer not found: {}", classRequest.getLecturerId());
                    return new RuntimeException("Lecturer not found");
                });

        log.info("Checking if semester exists: {}", classRequest.getSemesterId());
        Semester semester = semesterRepository.findById(classRequest.getSemesterId())
                .orElseThrow(() -> {
                    log.error("Semester not found: {}", classRequest.getSemesterId());
                    return new RuntimeException("Semester not found");
                });

        log.info("Setting course, lecturer and semester for class");
        classEntity.setCourse(course);
        classEntity.setLecturer(lecturer);
        classEntity.setSemester(semester);
        classEntity = classRepository.save(classEntity);

        log.info("Class added successfully: {}", classRequest.getClassCode());

        return ClassMapper.mapFromDomainToClassResponse(classEntity);
    }
}
