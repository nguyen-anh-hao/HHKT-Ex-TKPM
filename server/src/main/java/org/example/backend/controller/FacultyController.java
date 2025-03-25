package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.FacultyRequest;
import org.example.backend.dto.response.ApiResponse;
import org.example.backend.dto.response.FacultyResponse;
import org.example.backend.service.IFacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ApiResponse addFaculty(@RequestBody @Valid FacultyRequest request) {
        log.info("Received request to add faculty: {}", request.getFacultyName());

        FacultyResponse faculty = facultyService.addFaculty(request);

        log.info("Successfully added faculty: {}", faculty.getFacultyName());

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(faculty)
                .build();
    }
}
