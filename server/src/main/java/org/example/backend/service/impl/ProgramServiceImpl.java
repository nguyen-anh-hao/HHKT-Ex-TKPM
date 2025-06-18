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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ProgramResponse> getAllPrograms() {
        List<Program> programs = programRepository.findAll();

        log.info("Retrieved all programs from database");

        return programs.stream()
                .map(ProgramMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramResponse getProgramById(Integer id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Program not found");
                    return new RuntimeException("Program not found");
                });

        log.info("Retrieved program from database");

        return ProgramMapper.mapToResponse(program);
    }

    @Override
    public ProgramResponse updateProgram(Integer id, ProgramRequest request) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Program not found");
                    return new RuntimeException("Program not found");
                });

        program.setProgramName(request.getProgramName());

        program = programRepository.save(program);

        log.info("Program updated successfully");

        return ProgramMapper.mapToResponse(program);
    }

    @Override
    public void deleteProgram(Integer id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Program not found");
                    return new RuntimeException("Program not found");
                });

        programRepository.delete(program);

        log.info("Program deleted successfully");
    }
}