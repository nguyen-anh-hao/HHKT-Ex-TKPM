package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassServiceImpl implements IClassService {
    private final IClassRepository classRepository;
    private final ICourseRepository courseRepository;
    private final ILecturerRepository lecturerRepository;
    private final ISemesterRepository semesterRepository;

    @Override
    public Page<ClassResponse> getAllClasses(Pageable pageable) {
        log.info("Fetching all classes");

        Page<Class> classes = classRepository.findAll(pageable);

        return classes.map(ClassMapper::mapFromDomainToClassResponse);
    }

    @Override
    public ClassResponse getClassById(Integer classId) {
        log.info("Fetching class by id: {}", classId);

        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        return ClassMapper.mapFromDomainToClassResponse(classEntity);
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
