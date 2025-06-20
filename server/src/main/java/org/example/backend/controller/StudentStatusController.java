package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.StudentStatusRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.StudentStatusResponse;
import org.example.backend.service.IStudentStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student-statuses")
@RequiredArgsConstructor
@Slf4j
public class StudentStatusController {
    private final IStudentStatusService studentStatusService;

    @PostMapping("")
    @Operation(summary = "Add a new student status", description = "Create a new student status in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student status created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse addStudentStatus(@RequestBody @Valid StudentStatusRequest request) {
        log.info("Received request to add student status: {}", request.getStudentStatusName());

        StudentStatusResponse studentStatus = studentStatusService.addStudentStatus(request);

        log.info("Successfully added student status: {}", studentStatus.getStudentStatusName());

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(studentStatus)
                .build();
    }


    @GetMapping("")
    @Operation(summary = "Get all student statuses", description = "Retrieve a list of all student statuses in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of student statuses",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentStatusResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json")),
    })
    public APIResponse getAllStudentStatuses() {
        log.info("Received request to get all student statuses");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusService.getAllStudentStatuses())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a student status by ID", description = "Retrieve a specific student status by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved student status",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StudentStatusResponse.class))),
            @ApiResponse(responseCode = "404", description = "Student status not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json")),
    })
    public APIResponse getStudentStatusById(@PathVariable Integer id) {
        log.info("Received request to get student status with id: {}", id);

        StudentStatusResponse studentStatus = studentStatusService.getStudentStatusById(id);

        log.info("Successfully retrieved student status with id: {}", id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatus)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student status", description = "Update an existing student status in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student status updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student status not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse updateStudentStatus(@PathVariable Integer id, @RequestBody @Valid StudentStatusRequest request) {
        log.info("Received request to update student status with id: {}", id);

        StudentStatusResponse studentStatus = studentStatusService.updateStudentStatus(id, request);

        log.info("Successfully updated student status with id: {}", id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatus)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student status", description = "Delete an existing student status by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student status deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student status not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse deleteStudentStatus(@PathVariable Integer id) {
        log.info("Received request to delete student status with id: {}", id);

        studentStatusService.deleteStudentStatus(id);

        log.info("Successfully deleted student status with id: {}", id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
