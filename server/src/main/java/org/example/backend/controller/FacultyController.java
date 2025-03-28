package org.example.backend.controller;

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
    public APIResponse getAllFaculties() {
        log.info("Received request to get all faculties");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(facultyService.getAllFaculties())
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse getFacultyById(@PathVariable Integer id) {
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
    public APIResponse updateFaculty(@PathVariable Integer id, @RequestBody @Valid FacultyRequest request) {
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
    public APIResponse deleteFaculty(@PathVariable Integer id) {
        log.info("Received request to delete faculty with id: {}", id);

        facultyService.deleteFaculty(id);

        log.info("Successfully deleted faculty with id: {}", id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
