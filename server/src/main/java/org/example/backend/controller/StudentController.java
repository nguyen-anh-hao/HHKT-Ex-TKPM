package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.StudentRequest;
import org.example.backend.dto.response.ApiResponse;
import org.example.backend.dto.response.StudentResponse;
import org.example.backend.service.IStudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final IStudentService studentService;

    @PostMapping("")
    public ApiResponse addStudent(@RequestBody @Valid StudentRequest request) {
        log.info("Received request to add student: {}", request.getFullName());

        StudentResponse student = studentService.addStudent(request);

        log.info("Successfully added student: {}", student.getFullName());

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(student)
                .build();
    }

    @GetMapping("/{studentId}")
    public ApiResponse getStudent(@PathVariable String studentId) {
        log.info("Received request to get student with studentId: {}", studentId);

        StudentResponse student = studentService.getStudent(studentId);

        log.info("Successfully retrieved student with studentId: {}", studentId);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(student)
                .build();
    }

    @GetMapping("")
    public ApiResponse getAllStudents(@PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to get all students");

        Page<StudentResponse> studentPage = studentService.getAllStudents(pageable);

        log.info("Successfully retrieved all students");

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentPage.getContent())
                .paginationInfo(new PaginationInfo(studentPage))
                .build();
    }

    @PutMapping("/{studentId}")
    public ApiResponse updateStudent(@PathVariable String studentId, @RequestBody @Valid StudentRequest request) {
        log.info("Received request to update student with studentId: {}", studentId);

        StudentResponse student = studentService.updateStudent(studentId, request);

        log.info("Successfully updated student with studentId: {}", studentId);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(student)
                .build();
    }

    @DeleteMapping("/{studentId}")
    public ApiResponse deleteStudent(@PathVariable String studentId) {
        log.info("Received request to delete student with studentId: {}", studentId);

        studentService.deleteStudent(studentId);

        log.info("Successfully deleted student with studentId: {}", studentId);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse searchStudents(@RequestParam String keyword, @PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to search students with keyword: {}", keyword);

        Page<StudentResponse> studentPage = studentService.searchStudent(keyword, pageable);

        log.info("Successfully searched students with keyword: {}", keyword);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentPage.getContent())
                .paginationInfo(new PaginationInfo(studentPage))
                .build();
    }
}