package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Class;
import org.example.backend.domain.ClassRegistration;
import org.example.backend.domain.ClassRegistrationHistory;
import org.example.backend.domain.Student;
import org.example.backend.dto.request.ClassRegistrationHistoryRequest;
import org.example.backend.dto.request.ClassRegistrationRequest;
import org.example.backend.dto.response.ClassRegistrationHistoryResponse;
import org.example.backend.dto.response.ClassRegistrationResponse;
import org.example.backend.mapper.ClassRegistrationHistoryMapper;
import org.example.backend.mapper.ClassRegistrationMapper;
import org.example.backend.repository.IClassRegistrationRepository;
import org.example.backend.repository.IClassRepository;
import org.example.backend.repository.IStudentRepository;
import org.example.backend.service.IClassRegistrationHistoryService;
import org.example.backend.service.IClassRegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassRegistrationServiceImpl implements IClassRegistrationService {
    private final IClassRegistrationRepository classRegistrationRepository;
    private final IClassRepository classRepository;
    private final IStudentRepository studentRepository;
    private final IClassRegistrationHistoryService classRegistrationHistoryService;

    @Override
    public Page<ClassRegistrationResponse> getAllClassRegistrations(Pageable pageable) {

        log.info("get all class registrations");

        Page<ClassRegistration> classRegistrationPage = classRegistrationRepository.findAll(pageable);

        return classRegistrationPage.map(ClassRegistrationMapper::mapFromDomainToClassRegistrationResponse);
    }

    @Override
    public ClassRegistrationResponse getClassRegistrationById(Integer id) {

        log.info("get class registration by id: {}", id);

        ClassRegistration classRegistration = classRegistrationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Class registration not found");
                    return new RuntimeException("Class registration not found");
                });

        return ClassRegistrationMapper.mapFromDomainToClassRegistrationResponse(classRegistration);
    }

    @Override
    @Transactional
    public ClassRegistrationResponse addClassRegistration(ClassRegistrationRequest classRegistrationRequest) {

        log.info("add class registration with request: {}", classRegistrationRequest);

        ClassRegistration classRegistration = ClassRegistrationMapper.mapFromClassRegistrationRequestToDomain(classRegistrationRequest);

        log.info("check if class exists");
        Class aClass = classRepository.findById(classRegistrationRequest.getClassId())
                .orElseThrow(() -> {
                    log.error("Class not found");
                    return new RuntimeException("Class not found");
                });

        log.info("check if the course of the class is active");
        if (!aClass.getCourse().getIsActive()) {
            log.error("Course of the class is not active");
            throw new RuntimeException("Course of the class is not active");
        }

        log.info("check if max students reached");
        if (aClass.getClassRegistrations().size() >= aClass.getMaxStudents()) {
            log.error("Max students reached");
            throw new RuntimeException("Max students reached");
        }

        log.info("check if student exists");
        Student student = studentRepository.findById(classRegistrationRequest.getStudentId())
                .orElseThrow(() -> {
                    log.error("Student not found");
                    return new RuntimeException("Student not found");
                });

        log.info("set class and student");
        classRegistration.setAClass(aClass);
        classRegistration.setStudent(student);
        classRegistration = classRegistrationRepository.save(classRegistration);

        log.info("class registration created successfully");

        // Add the registration history
        log.info("Add class registration history");
        ClassRegistrationHistoryRequest classRegistrationHistoryRequest = ClassRegistrationHistoryMapper.mapFromClassRegistrationDomainToClassRegistrationHistoryRequest(classRegistration);

        classRegistrationHistoryService.addClassRegistrationHistory(classRegistrationHistoryRequest);
        log.info("class registration history added successfully");

        return ClassRegistrationMapper.mapFromDomainToClassRegistrationResponse(classRegistration);
    }
}
