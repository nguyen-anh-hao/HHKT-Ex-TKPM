package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Add a new program", description = "Create a new program in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Program added successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgramResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
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
    @Operation(summary = "Get all programs", description = "Retrieve the list of all available programs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Programs retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProgramResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public APIResponse getAllPrograms() {
        log.info("Received request to get all programs");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(programService.getAllPrograms())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get program by ID", description = "Retrieve the details of a specific program by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgramResponse.class))),
            @ApiResponse(responseCode = "404", description = "Program not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public APIResponse getProgramById(
            @Parameter(description = "ID of the program to retrieve", required = true, example = "1")
            @PathVariable Integer id) {
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
    @Operation(summary = "Update a program", description = "Update the details of an existing program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgramResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Program not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public APIResponse updateProgram(
            @Parameter(description = "ID of the program to update", required = true, example = "1")
            @PathVariable Integer id,

            @RequestBody @Valid ProgramRequest request) {
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
    @Operation(summary = "Delete a program", description = "Delete a program by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Program not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    public APIResponse deleteProgram(
            @Parameter(description = "ID of the program to delete", required = true, example = "1")
            @PathVariable Integer id) {
        log.info("Received request to delete program with id: {}", id);

        programService.deleteProgram(id);

        log.info("Successfully deleted program with id: {}", id);
        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
