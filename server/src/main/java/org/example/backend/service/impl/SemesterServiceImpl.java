package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Semester;
import org.example.backend.dto.request.SemesterRequest;
import org.example.backend.dto.response.SemesterResponse;
import org.example.backend.mapper.SemesterMapper;
import org.example.backend.repository.ISemesterRepository;
import org.example.backend.service.ISemesterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SemesterServiceImpl implements ISemesterService {
    private final ISemesterRepository semesterRepository;

    @Override
    public Page<SemesterResponse> getAllSemesters(Pageable pageable) {
        log.info("Fetching all semesters");

        Page<Semester> semesterPage = semesterRepository.findAll(pageable);

        return semesterPage.map(SemesterMapper::mapFromSemesterDomainToSemesterResponse);
    }

    @Override
    public SemesterResponse getSemesterById(Integer semesterId) {
        log.info("Fetching semester by id: {}", semesterId);

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        return SemesterMapper.mapFromSemesterDomainToSemesterResponse(semester);
    }

    @Override
    @Transactional
    public SemesterResponse addSemester(SemesterRequest semesterRequest) {
        log.info("Adding semester: {}", semesterRequest.getSemester());

        log.info("Checking if semester already exists");
        // semester and academic year should be unique
        if (semesterRepository.findBySemesterAndAcademicYear(semesterRequest.getSemester(), semesterRequest.getAcademicYear()).isPresent()) {
            log.error("Semester already exists");
            throw new RuntimeException("Semester already exists");
        }

        Semester semester = SemesterMapper.mapFromSemesterRequestToSemesterDomain(semesterRequest);

        Semester savedSemester = semesterRepository.save(semester);

        log.info("Semester saved to database successfully");

        return SemesterMapper.mapFromSemesterDomainToSemesterResponse(savedSemester);
    }
}
