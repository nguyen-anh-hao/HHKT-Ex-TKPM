package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Program;
import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ProgramResponse;
import org.example.backend.mapper.ProgramMapper;
import org.example.backend.repository.IProgramRepository;
import org.example.backend.service.IProgramService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramServiceImpl implements IProgramService {
    private final IProgramRepository programRepository;

    @Override
    public ProgramResponse addProgram(ProgramRequest request) {
        if (programRepository.findByProgramName(request.getProgramName()).isPresent()) {
            log.error("Program already exists");
            throw new RuntimeException("Program already exists");
        }

        Program program = ProgramMapper.mapToDomain(request);
        program = programRepository.save(program);

        log.info("Program saved to database successfully");

        return ProgramMapper.mapToResponse(program);
    }
}
