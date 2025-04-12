package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.SemesterRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.SemesterResponse;
import org.example.backend.service.ISemesterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/semesters")
public class SemesterController {

    private final ISemesterService semesterService;

    @GetMapping("")
    public APIResponse getSemesters(@PageableDefault(size = 10, page = 0) Pageable pageable) {

        log.info("Fetching semesters");

        Page<SemesterResponse> semesterResponsePage = semesterService.getAllSemesters(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Semesters fetched successfully")
                .data(semesterResponsePage.getContent())
                .paginationInfo(new PaginationInfo(semesterResponsePage))
                .build();


    }

    @GetMapping("/{semesterId}")
    public APIResponse getSemesterById(@PathVariable Integer semesterId) {

        log.info("Fetching semester by id: {}", semesterId);

        SemesterResponse semesterResponse = semesterService.getSemesterById(semesterId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Semester fetched successfully")
                .data(semesterResponse)
                .build();


    }

    @PostMapping("")
    public APIResponse addSemester(@RequestBody @Valid SemesterRequest semesterRequest) {

        log.info("Adding semester: {}", semesterRequest.getSemester());

        SemesterResponse addedSemester = semesterService.addSemester(semesterRequest);

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Semester added successfully")
                .data(addedSemester)
                .build();


    }
}
