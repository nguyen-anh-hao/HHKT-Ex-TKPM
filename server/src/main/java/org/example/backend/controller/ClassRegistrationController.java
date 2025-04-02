package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.ClassRegistrationRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.ClassRegistrationResponse;
import org.example.backend.service.IClassRegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/class-registrations")
@Slf4j
@RequiredArgsConstructor
public class ClassRegistrationController {

    private final IClassRegistrationService classRegistrationService;

    @GetMapping("")
    public APIResponse getAllClassRegistrations(@PageableDefault(size = 3, page = 0) Pageable pageable) {

        log.info("getAllClassRegistrations() called");

        Page<ClassRegistrationResponse> classRegistrationResponsePage = classRegistrationService
                .getAllClassRegistrations(pageable);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Data retrieved")
                .data(classRegistrationResponsePage.getContent())
                .paginationInfo(new PaginationInfo(classRegistrationResponsePage))
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse getClassRegistrationById(@PathVariable Integer id) {

        log.info("get class registration by id: {}", id);

        ClassRegistrationResponse classRegistrationResponse = classRegistrationService
                .getClassRegistrationById(id);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Data retrieved")
                .data(classRegistrationResponse)
                .build();
    }

    @PostMapping("")
    public APIResponse createClassRegistration(@RequestBody @Valid ClassRegistrationRequest classRegistrationRequest) {

        log.info("create class registration with request: {}", classRegistrationRequest);

        ClassRegistrationResponse classRegistrationResponse = classRegistrationService
                .addClassRegistration(classRegistrationRequest);

        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Data created")
                .data(classRegistrationResponse)
                .build();
    }
}
