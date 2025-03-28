package org.example.backend.controller;

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
    public APIResponse getAllStudentStatuses() {
        log.info("Received request to get all student statuses");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(studentStatusService.getAllStudentStatuses())
                .build();
    }

    @GetMapping("/{id}")
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
