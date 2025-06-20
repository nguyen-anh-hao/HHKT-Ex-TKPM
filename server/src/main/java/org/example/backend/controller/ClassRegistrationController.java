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
import org.example.backend.dto.request.ClassRegistrationRequest;
import org.example.backend.dto.request.ClassRegistrationUpdateRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.ClassRegistrationResponse;
import org.example.backend.service.IClassRegistrationService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/class-registrations")
@Slf4j
@RequiredArgsConstructor
public class ClassRegistrationController {

    private final IClassRegistrationService classRegistrationService;

    @GetMapping("")
    @Operation(summary = "Get all class registrations", description = "Retrieve a paginated list of class registrations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class registrations retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassRegistrationResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getAllClassRegistrations(
            @ParameterObject  @PageableDefault(size = 3, page = 0) Pageable pageable) {

        log.info("Received request to get all class registrations");

        Page<ClassRegistrationResponse> classRegistrationResponsePage = classRegistrationService
                .getAllClassRegistrations(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Data retrieved")
                .data(classRegistrationResponsePage.getContent())
                .paginationInfo(new PaginationInfo(classRegistrationResponsePage))
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a class registration by ID", description = "Retrieve a specific class registration by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class registration retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassRegistrationResponse.class))),
            @ApiResponse(responseCode = "404", description = "Class registration not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getClassRegistrationById(
            @Parameter(description = "ID of the class registration to retrieve", required = true, example = "1")
            @PathVariable Integer id) {

        log.info("Received request to get class registration with id: {}", id);

        ClassRegistrationResponse classRegistrationResponse = classRegistrationService
                .getClassRegistrationById(id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Data retrieved")
                .data(classRegistrationResponse)
                .build();
    }

    @PostMapping("")
    @Operation(summary = "Create a class registration", description = "Register a student to a class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Class registration created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassRegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse createClassRegistration(@RequestBody @Valid ClassRegistrationRequest classRegistrationRequest) {

        log.info("Received request to create class registration with request: {}", classRegistrationRequest);

        ClassRegistrationResponse classRegistrationResponse = classRegistrationService
                .addClassRegistration(classRegistrationRequest);

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Data created")
                .data(classRegistrationResponse)
                .build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a class registration", description = "Update status or grade of a class registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class registration updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassRegistrationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Class registration not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse updateClassRegistration(
            @Parameter(description = "ID of the class registration to update", required = true, example = "1")
            @PathVariable Integer id,

            @RequestBody @Valid ClassRegistrationUpdateRequest classRegistrationUpdateRequest) {

        log.info("Received request to update class registration with id: {} and request: {}", id, classRegistrationUpdateRequest);

        ClassRegistrationResponse classRegistrationResponse = classRegistrationService.
                updateClassRegistration(id, classRegistrationUpdateRequest);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Data updated")
                .data(classRegistrationResponse)
                .build();
    }
}
