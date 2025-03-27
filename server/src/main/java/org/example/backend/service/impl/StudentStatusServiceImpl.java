package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.StudentStatus;
import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.StudentStatusResponse;
import org.example.backend.mapper.StudentStatusMapper;
import org.example.backend.repository.IStudentStatusRepository;
import org.example.backend.service.IStudentStatusService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentStatusServiceImpl implements IStudentStatusService {
    private final IStudentStatusRepository studentStatusRepository;

    @Override
    public StudentStatusResponse addStudentStatus(StudentStatusRequest request) {
        if (studentStatusRepository.findByStudentStatusName(request.getStudentStatusName()).isPresent()) {
            log.error("Student status already exists");
            throw new RuntimeException("Student status already exists");
        }

        StudentStatus studentStatus = StudentStatusMapper.toDomainFromRequestDTO(request);
        studentStatus = studentStatusRepository.save(studentStatus);

        log.info("Student status saved to database successfully");

        return StudentStatusMapper.toResponseDTO(studentStatus);
    }

    @Override
    public String getStudentStatusName(Integer studentStatusId) {
        log.info("Received request to get student status name with studentStatusId: {}", studentStatusId);

        return studentStatusRepository.findById(studentStatusId)
                .orElseThrow(
                        () -> {
                            log.error("Student status not found");
                            return new RuntimeException("Student status not found");
                        })
                .getStudentStatusName();
    }

}
