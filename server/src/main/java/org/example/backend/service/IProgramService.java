package org.example.backend.service;

import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ProgramResponse;

public interface IProgramService {
    ProgramResponse addProgram(ProgramRequest request);
}
