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
import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.FacultyResponse;
import org.example.backend.service.IFacultyService;
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
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
@Slf4j
public class FacultyController {
    private final IFacultyService facultyService;

    @PostMapping("")
    @Operation(summary = "Add a new faculty", description = "Create a new faculty in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Faculty added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacultyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse addFaculty(@RequestBody @Valid FacultyRequest request) {
        log.info("Received request to add faculty: {}", request.getFacultyName());

        FacultyResponse faculty = facultyService.addFaculty(request);

        log.info("Successfully added faculty: {}", faculty.getFacultyName());

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(faculty)
                .build();
    }

    @GetMapping("")
    @Operation(summary = "Get all faculties", description = "Retrieve a list of all faculties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faculties retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FacultyResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getAllFaculties() {
        log.info("Received request to get all faculties");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(facultyService.getAllFaculties())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a faculty by ID", description = "Retrieve faculty details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faculty retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacultyResponse.class))),
            @ApiResponse(responseCode = "404", description = "Faculty not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getFacultyById(
            @Parameter(description = "ID of the faculty to retrieve", required = true, example = "1")
            @PathVariable Integer id) {
        log.info("Received request to get faculty with id: {}", id);

        FacultyResponse faculty = facultyService.getFacultyById(id);

        log.info("Successfully retrieved faculty with id: {}", id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(faculty)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update faculty", description = "Update an existing faculty by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faculty updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacultyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Faculty not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse updateFaculty(
            @Parameter(description = "ID of the faculty to update", required = true, example = "1")
            @PathVariable Integer id, @RequestBody @Valid FacultyRequest request) {
        log.info("Received request to update faculty: {}", request.getFacultyName());

        FacultyResponse faculty = facultyService.updateFaculty(id, request);

        log.info("Successfully updated faculty: {}", faculty.getFacultyName());

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(faculty)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete faculty", description = "Delete a faculty by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Faculty deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Faculty not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse deleteFaculty(
            @Parameter(description = "ID of the faculty to delete", required = true, example = "1")
            @PathVariable Integer id) {
        log.info("Received request to delete faculty with id: {}", id);

        facultyService.deleteFaculty(id);

        log.info("Successfully deleted faculty with id: {}", id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
