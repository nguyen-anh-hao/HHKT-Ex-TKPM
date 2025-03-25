package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.ApiResponse;
import org.example.backend.dto.response.ProgramResponse;
import org.example.backend.service.IProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
@Slf4j
public class ProgramController {
    private final IProgramService programService;

    @PostMapping("")
    public ApiResponse addProgram(@RequestBody @Valid ProgramRequest request) {
        log.info("Received request to add program: {}", request.getProgramName());

        ProgramResponse program = programService.addProgram(request);

        log.info("Successfully added program: {}", program.getProgramName());
        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(program)
                .build();
    }
}
