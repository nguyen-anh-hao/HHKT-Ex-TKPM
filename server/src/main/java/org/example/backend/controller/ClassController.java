package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.ClassRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.ClassResponse;
import org.example.backend.service.IClassService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
@Slf4j
@RequiredArgsConstructor
public class ClassController {
    private final IClassService classService;

    @GetMapping("")
    public APIResponse getAllClasses(@PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to get all classes");

        Page<ClassResponse> classResponsePage = classService.getAllClasses(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(classResponsePage.getContent())
                .paginationInfo(new PaginationInfo(classResponsePage))
                .build();
    }

    @GetMapping("/{classId}")
    public APIResponse getClassById(@PathVariable Integer classId) {
        log.info("Received request to get class by id: {}", classId);

        ClassResponse classResponse = classService.getClassById(classId);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(classResponse)
                .build();
    }

    @PostMapping("")
    public APIResponse addClass(@RequestBody @Valid ClassRequest classRequest) {
        log.info("Received request to add class: {}", classRequest.getClassCode());

        ClassResponse classResponse = classService.addClass(classRequest);

        log.info("Successfully added class: {}", classResponse.getClassCode());

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(classResponse)
                .build();
    }
}
