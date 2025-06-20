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
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.LecturerRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.LecturerResponse;
import org.example.backend.service.ILecturerService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecturers")
@Slf4j
@RequiredArgsConstructor
public class LecturerController {

    private final ILecturerService lecturerService;

    @PostMapping("")
    @Operation(summary = "Add a new lecturer", description = "Create a new lecturer in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lecturer added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LecturerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse addLecturer(@RequestBody @Valid LecturerRequest request) {

        log.info("Received request to add lecturer: {}", request.getFullName());

        LecturerResponse lecturerResponse = lecturerService.addLecturer(request);

        log.info("Successfully added lecturer: {}", lecturerResponse.getFullName());

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(lecturerResponse)
                .build();
    }

    @GetMapping("")
    @Operation(summary = "Get all lecturers", description = "Retrieve all lecturers with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lecturers retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LecturerResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getAllLecturers(
            @ParameterObject @PageableDefault(size = 3, page = 0) Pageable pageable) {

        log.info("Received request to get all lecturers");

        Page<LecturerResponse> lecturerResponsePage = lecturerService.getAllLecturers(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(lecturerResponsePage.getContent())
                .paginationInfo(new PaginationInfo(lecturerResponsePage))
                .build();
    }

    @GetMapping("/{lecturerId}")
    @Operation(summary = "Get lecturer by ID", description = "Retrieve a lecturer by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lecturer retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LecturerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Lecturer not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getLecturerById(
            @Parameter(description = "ID of the lecturer to retrieve", required = true, example = "1")
            @PathVariable Integer lecturerId) {

        log.info("Received request to get lecturer by id: {}", lecturerId);

        LecturerResponse lecturerResponse = lecturerService.getLecturerById(lecturerId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(lecturerResponse)
                .build();
    }
}
