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
import org.example.backend.dto.request.StudentStatusRuleRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.StudentStatusRuleResponse;
import org.example.backend.service.IStudentStatusRuleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/api/student-status-rules")
@RequiredArgsConstructor
@Slf4j
public class StudentStatusRuleController {
    private final IStudentStatusRuleService studentStatusRuleService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a student status rule by ID",
            description = "Retrieve a specific student status rule by its unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the student status rule",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentStatusRuleResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Student status rule not found",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    public APIResponse getStudentStatusRuleById(
            @Parameter(description = "Unique ID of the student status rule", required = true)
            @PathVariable Integer id) {
        log.info("Received request to get student status rule by ID: {}", id);
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.getStudentStatusRuleById(id);

        if (studentStatusRule == null) {
            return APIResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Student status rule not found")
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusRule)
                .build();
    }

    @GetMapping("")
    @Operation(
            summary = "Get all student status rules with pagination",
            description = "Retrieve a paginated list of all student status rules."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of student status rules",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentStatusRuleResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "No student status rules found",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    public APIResponse getAllStudentStatusRules(@PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to get all student status rules with pagination: {}", pageable);

        Page<StudentStatusRuleResponse> studentStatusRules = studentStatusRuleService.getAllStudentStatusRules(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusRules.getContent())
                .paginationInfo(new PaginationInfo(studentStatusRules))
                .build();
    }

    @PostMapping("")
    @Operation(
            summary = "Add a new student status rule",
            description = "Create a new student status rule with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created the student status rule",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = StudentStatusRuleResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public APIResponse addStudentStatusRule(@RequestBody @Valid StudentStatusRuleRequest request) {
        log.info("Adding new student status rule: {}", request);
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.addStudentStatusRule(request);

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(studentStatusRule)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a student status rule",
            description = "Update an existing student status rule with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the student status rule",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StudentStatusRuleResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Student status rule not found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public APIResponse updateStudentStatusRule(
            @Parameter(description = "Unique ID of the student status rule to update", required = true)
            @PathVariable Integer id,

            @Valid @RequestBody StudentStatusRuleRequest request) {
        log.info("Updating student status rule with ID: {}, Request: {}", id, request);
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.updateStudentStatusRule(id, request);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusRule)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a student status rule",
            description = "Delete an existing student status rule by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted the student status rule",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Student status rule not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class)
                            )
                    )
            }
    )
    public APIResponse deleteStudentStatusRule(
            @Parameter(description = "Unique ID of the student status rule to delete", required = true)
            @PathVariable Integer id) {
        log.info("Deleting student status rule with ID: {}", id);

        // Check if the student status rule exists
        StudentStatusRuleResponse studentStatusRule = studentStatusRuleService.getStudentStatusRuleById(id);
        if (studentStatusRule == null) {
            return APIResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Student status rule not found")
                    .build();
        }


        studentStatusRuleService.deleteStudentStatusRule(id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
