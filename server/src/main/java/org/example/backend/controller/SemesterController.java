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
import org.example.backend.dto.request.SemesterRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.SemesterResponse;
import org.example.backend.service.ISemesterService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/semesters")
public class SemesterController {

    private final ISemesterService semesterService;

    @GetMapping("")
    @Operation(
            summary = "Get all semesters",
            description = "Retrieve a paginated list of all semesters"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Semesters fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SemesterResponse.class))
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getSemesters(
            @ParameterObject @PageableDefault(size = 10, page = 0) Pageable pageable) {

        log.info("Received request to get all semesters");

        Page<SemesterResponse> semesterResponsePage = semesterService.getAllSemesters(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Semesters fetched successfully")
                .data(semesterResponsePage.getContent())
                .paginationInfo(new PaginationInfo(semesterResponsePage))
                .build();


    }

    @GetMapping("/{semesterId}")
    @Operation(
            summary = "Get semester by ID",
            description = "Retrieve the semester details by its unique ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Semester fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SemesterResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Semester not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getSemesterById(
            @Parameter(description = "ID of the semester", required = true, example = "1")
            @PathVariable Integer semesterId) {

        log.info("Received request to get semester with ID: {}", semesterId);

        SemesterResponse semesterResponse = semesterService.getSemesterById(semesterId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Semester fetched successfully")
                .data(semesterResponse)
                .build();


    }

    @PostMapping("")
    @Operation(
            summary = "Add a new semester",
            description = "Create a new semester in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Semester added successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SemesterResponse.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse addSemester(@RequestBody @Valid SemesterRequest semesterRequest) {

        log.info("Received request to add a new semester: {}", semesterRequest);

        SemesterResponse addedSemester = semesterService.addSemester(semesterRequest);

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Semester added successfully")
                .data(addedSemester)
                .build();


    }
}
