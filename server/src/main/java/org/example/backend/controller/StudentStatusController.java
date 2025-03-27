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
}
