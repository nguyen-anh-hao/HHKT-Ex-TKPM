package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Faculty;
import org.example.backend.domain.Lecturer;
import org.example.backend.dto.request.LecturerRequest;
import org.example.backend.dto.response.LecturerResponse;
import org.example.backend.mapper.LecturerMapper;
import org.example.backend.repository.IFacultyRepository;
import org.example.backend.repository.ILecturerRepository;
import org.example.backend.service.ILecturerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LecturerServiceImpl implements ILecturerService {
    private final ILecturerRepository lecturerRepository;
    private final IFacultyRepository facultyRepository;

    @Override
    public Page<LecturerResponse> getAllLecturers(Pageable pageable) {
        log.info("Getting all lecturers");

        Page<Lecturer> lecturerPage = lecturerRepository.findAll(pageable);

        return lecturerPage.map(LecturerMapper::mapFromDomainToResponse);
    }

    @Override
    public LecturerResponse getLecturerById(Integer lecturerId) {
        log.info("Getting lecturer by id: {}", lecturerId);

        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        return LecturerMapper.mapFromDomainToResponse(lecturer);
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