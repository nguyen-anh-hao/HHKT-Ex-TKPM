package org.example.backend.service;

import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ProgramResponse;

import java.util.List;

public interface IProgramService {
    ProgramResponse addProgram(ProgramRequest request);
    List<ProgramResponse> getAllPrograms();
    ProgramResponse getProgramById(Integer id);
    ProgramResponse updateProgram(Integer id, ProgramRequest request);
    void deleteProgram(Integer id);
}
