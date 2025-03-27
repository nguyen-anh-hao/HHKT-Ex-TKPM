package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.request.StudentUpdateRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.service.IStudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Student Management", description = "API for managing students")
public class StudentController {

    private final IStudentService studentService;

    @PostMapping("")
    @Operation(summary = "Add a new student", description = "Add a new student to the system")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "201", description = "Student added successfully",
            content =  @Content(mediaType = "application/json",
            schema =  @Schema(implementation = StudentResponse.class))),
            @ApiResponse (responseCode = "400", description = "Invalid input",
            content =  @Content(mediaType = "application/json")),
            @ApiResponse (responseCode = "500", description = "Internal server error",
            content =  @Content(mediaType = "application/json"))
    })
    public APIResponse addStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student details to add", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentRequest.class)))
            @RequestBody @Valid StudentRequest request) {
        log.info("Received request to add student: {}", request.getFullName());

        StudentResponse student = studentService.addStudent(request);

        log.info("Successfully added student: {}", student.getFullName());

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(student)
                .build();
    }

    @GetMapping("/{studentId}")
    @Operation(summary = "Get student by studentId", description = "Get student details by studentId")
    public APIResponse getStudent(@PathVariable String studentId) {
        log.info("Received request to get student with studentId: {}", studentId);

        StudentResponse student = studentService.getStudent(studentId);

        log.info("Successfully retrieved student with studentId: {}", studentId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(student)
                .build();
    }

    @GetMapping("")
    public APIResponse getAllStudents(@PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to get all students");

        Page<StudentResponse> studentPage = studentService.getAllStudents(pageable);

        log.info("Successfully retrieved all students");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentPage.getContent())
                .paginationInfo(new PaginationInfo(studentPage))
                .build();
    }

    @PatchMapping("/{studentId}")
    public APIResponse updateStudent(@PathVariable String studentId, @Valid @RequestBody StudentUpdateRequest request) {
        log.info("Received request to update student with studentId: {}", studentId);

        StudentResponse student = studentService.updateStudent(studentId, request);

        log.info("Successfully updated student with studentId: {}", studentId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(student)
                .build();
    }

    @DeleteMapping("/{studentId}")
    public APIResponse deleteStudent(@PathVariable String studentId) {
        log.info("Received request to delete student with studentId: {}", studentId);

        studentService.deleteStudent(studentId);

        log.info("Successfully deleted student with studentId: {}", studentId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }

    @GetMapping("/search")
    public APIResponse searchStudents(@RequestParam String keyword, @PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to search students with keyword: {}", keyword);

        Page<StudentResponse> studentPage = studentService.searchStudent(keyword, pageable);

        log.info("Successfully searched students with keyword: {}", keyword);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentPage.getContent())
                .paginationInfo(new PaginationInfo(studentPage))
                .build();
    }
}