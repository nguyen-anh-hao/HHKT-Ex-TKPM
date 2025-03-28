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

import java.util.List;
import java.util.stream.Collectors;

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
    public List<StudentStatusResponse> getAllStudentStatuses() {
        List<StudentStatus> studentStatuses = studentStatusRepository.findAll();

        log.info("Retrieved all student statuses from database");

        return studentStatuses.stream()
                .map(StudentStatusMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentStatusResponse getStudentStatusById(Integer studentStatusId) {
        StudentStatus studentStatus = studentStatusRepository.findById(studentStatusId)
                .orElseThrow(() -> {
                    log.error("Student status not found");
                    return new RuntimeException("Student status not found");
                });

        log.info("Retrieved student status from database");

        return StudentStatusMapper.toResponseDTO(studentStatus);
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

        return StudentStatusMapper.toResponseDTO(studentStatus);
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
