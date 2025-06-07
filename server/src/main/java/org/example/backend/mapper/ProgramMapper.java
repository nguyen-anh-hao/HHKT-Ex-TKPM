package org.example.backend.mapper;

import org.example.backend.domain.Program;
import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ProgramResponse;
import org.example.backend.dto.response.StudentResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .students(program.getStudents() != null ? program.getStudents().stream().map(StudentMapper::mapToResponse).collect(Collectors.toList()) : null)
                .createdAt(program.getCreatedAt())
                .updatedAt(program.getUpdatedAt())
                .createdBy(program.getCreatedBy())
                .updatedBy(program.getUpdatedBy())
                .build();
    }
}
