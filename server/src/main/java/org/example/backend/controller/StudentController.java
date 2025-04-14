package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
         @Valid  @RequestBody StudentRequest request) {
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
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Student retrieved successfully",
            content =  @Content(mediaType = "application/json",
            schema =  @Schema(implementation = StudentResponse.class))),
            @ApiResponse (responseCode = "404", description = "Student not found",
            content =  @Content(mediaType = "application/json")),
            @ApiResponse (responseCode = "500", description = "Internal server error",
            content =  @Content(mediaType = "application/json"))
    })
    public APIResponse getStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student ID to retrieve", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))
            )
            @PathVariable String studentId) {
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
    @Operation(summary = "Get all students", description = "Get all students in the system")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Students retrieved successfully",
            content =  @Content(mediaType = "application/json",
            schema =  @Schema(implementation = StudentResponse.class))),
            @ApiResponse (responseCode = "500", description = "Internal server error",
            content =  @Content(mediaType = "application/json"))
    })
    public APIResponse getAllStudents(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Page number and size",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pageable.class))
            )
            @PageableDefault(size = 3, page = 0) Pageable pageable) {
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
    @Operation(summary = "Update student", description = "Update student details")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Student updated successfully",
            content =  @Content(mediaType = "application/json",
            schema =  @Schema(implementation = StudentResponse.class))),
            @ApiResponse (responseCode = "404", description = "Student not found",
            content =  @Content(mediaType = "application/json")),
            @ApiResponse (responseCode = "500", description = "Internal server error",
            content =  @Content(mediaType = "application/json"))
    })
    public APIResponse updateStudent(
            @Parameter(description = "ID of the student to update", required = true,
                    example = "SV021")
            @PathVariable String studentId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Student details to update", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentUpdateRequest.class)))
            @Valid @RequestBody StudentUpdateRequest request) {
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
    @Operation(summary = "Delete student", description = "Delete student by studentId")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Student deleted successfully",
            content =  @Content(mediaType = "application/json")),
            @ApiResponse (responseCode = "404", description = "Student not found",
            content =  @Content(mediaType = "application/json")),
            @ApiResponse (responseCode = "500", description = "Internal server error",
            content =  @Content(mediaType = "application/json"))
    })
    public APIResponse deleteStudent(
            @Parameter(description = "ID of the student to delete", required = true,
                    example = "SV021")
            @PathVariable String studentId) {
        log.info("Received request to delete student with studentId: {}", studentId);

        studentService.deleteStudent(studentId);

        log.info("Successfully deleted student with studentId: {}", studentId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully deleted student with ID: " + studentId)
                .build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search students", description = "Search students by keyword")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Students retrieved successfully",
            content =  @Content(mediaType = "application/json",
            schema =  @Schema(implementation = StudentResponse.class))),
            @ApiResponse (responseCode = "500", description = "Internal server error",
            content =  @Content(mediaType = "application/json"))
    })
    public APIResponse searchStudents(
            @Parameter(description = "Keyword to search", required = true,
                examples = @ExampleObject(name = "keyword", value = "John")
            )
            @RequestParam String keyword,
            @Parameter(description = "Page number and size",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pageable.class),
                            examples = @ExampleObject(name = "page", value = "0")
                    ))
            @PageableDefault(size = 3, page = 0) Pageable pageable) {
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

    @GetMapping("/{studentId}/transcript")
    @Operation(summary = "Get student transcript", description = "Get student transcript by studentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transcript retrieved successfully",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<byte[]> getStudentTranscript(
            @Parameter(description = "ID of the student to retrieve transcript", required = true, example = "SV001")
            @PathVariable String studentId) {

        log.info("Received request to get transcript for student with studentId: {}", studentId);

        byte[] pdfBytes = studentService.getStudentTranscript(studentId);

        log.info("Successfully retrieved transcript for student with studentId: {}", studentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("transcript_" + studentId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}