package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.ClassRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.ClassResponse;
import org.example.backend.service.IClassService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
@Slf4j
@RequiredArgsConstructor
public class ClassController {
    private final IClassService classService;

    @GetMapping("")
    @Operation(summary = "Get all classes", description = "Retrieve all classes with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classes retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getAllClasses(
            @ParameterObject
            @PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to get all classes");

        Page<ClassResponse> classResponsePage = classService.getAllClasses(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(classResponsePage.getContent())
                .paginationInfo(new PaginationInfo(classResponsePage))
                .build();
    }

    @GetMapping("/{classId}")
    @Operation(summary = "Get class by ID", description = "Retrieve class details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassResponse.class))),
            @ApiResponse(responseCode = "404", description = "Class not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getClassById(
            @Parameter(description = "ID of the class to retrieve", required = true, example = "1")
            @PathVariable Integer classId) {
        log.info("Received request to get class by id: {}", classId);

        ClassResponse classResponse = classService.getClassById(classId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(classResponse)
                .build();
    }

    @PostMapping("")
    @Operation(summary = "Add a new class", description = "Create a new class with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Class created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse addClass(@RequestBody @Valid ClassRequest classRequest) {
        log.info("Received request to add class: {}", classRequest.getClassCode());

        ClassResponse classResponse = classService.addClass(classRequest);

        log.info("Successfully added class: {}", classResponse.getClassCode());

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(classResponse)
                .build();
    }
}
