package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.LecturerRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.LecturerResponse;
import org.example.backend.service.ILecturerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecturers")
@Slf4j
@RequiredArgsConstructor
public class LecturerController {

    private final ILecturerService lecturerService;

    @PostMapping("")
    public APIResponse addLecturer(@RequestBody @Valid LecturerRequest request) {

        log.info("Received request to add lecturer: {}", request.getFullName());

        LecturerResponse lecturerResponse = lecturerService.addLecturer(request);

        log.info("Successfully added lecturer: {}", lecturerResponse.getFullName());

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(lecturerResponse)
                .build();
    }

    @GetMapping("")
    public APIResponse getAllLecturers(@PageableDefault(size = 3, page = 0) Pageable pageable) {

        log.info("Received request to get all lecturers");

        Page<LecturerResponse> lecturerResponsePage = lecturerService.getAllLecturers(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(lecturerResponsePage.getContent())
                .paginationInfo(new PaginationInfo(lecturerResponsePage))
                .build();
    }

    @GetMapping("/{lecturerId}")
    public APIResponse getLecturerById(@PathVariable Integer lecturerId) {

        log.info("Received request to get lecturer by id: {}", lecturerId);

        LecturerResponse lecturerResponse = lecturerService.getLecturerById(lecturerId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(lecturerResponse)
                .build();
    }
}
