package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.ProgramRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.ProgramResponse;
import org.example.backend.service.IProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public APIResponse addProgram(@RequestBody @Valid ProgramRequest request) {
        log.info("Received request to add program: {}", request.getProgramName());

        ProgramResponse program = programService.addProgram(request);

        log.info("Successfully added program: {}", program.getProgramName());
        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(program)
                .build();
    }

    @GetMapping("")
    public APIResponse getAllPrograms() {
        log.info("Received request to get all programs");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(programService.getAllPrograms())
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse getProgramById(@PathVariable Integer id) {
        log.info("Received request to get program with id: {}", id);

        ProgramResponse program = programService.getProgramById(id);

        log.info("Successfully retrieved program with id: {}", id);
        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(program)
                .build();
    }

    @PutMapping("/{id}")
    public APIResponse updateProgram(@PathVariable Integer id, @RequestBody @Valid ProgramRequest request) {
        log.info("Received request to update program with id: {}", id);

        ProgramResponse program = programService.updateProgram(id, request);

        log.info("Successfully updated program with id: {}", id);
        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(program)
                .build();
    }

    @DeleteMapping("/{id}")
    public APIResponse deleteProgram(@PathVariable Integer id) {
        log.info("Received request to delete program with id: {}", id);

        programService.deleteProgram(id);

        log.info("Successfully deleted program with id: {}", id);
        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
