package org.example.backend.mapper;

import org.example.backend.domain.Program;
import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ProgramResponse;

public class ProgramMapper {
    public static Program mapToDomain(ProgramRequest request) {
        return Program.builder()
                .programName(request.getProgramName())
                .build();
    }

    public static ProgramResponse mapToResponse(Program program) {
        return ProgramResponse.builder()
                .id(program.getId())
                .programName(program.getProgramName())
                .createdAt(program.getCreatedAt())
                .updatedAt(program.getUpdatedAt())
                .createdBy(program.getCreatedBy())
                .updatedBy(program.getUpdatedBy())
                .build();
    }
}
