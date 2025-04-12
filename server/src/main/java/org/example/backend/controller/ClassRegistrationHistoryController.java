package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.ClassRegistrationHistory;
import org.example.backend.dto.request.ClassRegistrationHistoryRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.ClassRegistrationHistoryResponse;
import org.example.backend.service.IClassRegistrationHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/class-registration-histories")
@RequiredArgsConstructor
public class ClassRegistrationHistoryController {
    private final IClassRegistrationHistoryService classRegistrationHistoryService;

    @GetMapping("/")
    public String get() {
        return "Hello World!";
    }

    @PostMapping("")
    public APIResponse addClassRegistrationHistory(@RequestBody @Valid ClassRegistrationHistoryRequest classRegistrationHistoryRequest) {

        log.info("Request: {}", classRegistrationHistoryRequest);

        ClassRegistrationHistoryResponse classRegistrationHistoryResponse = classRegistrationHistoryService.addClassRegistrationHistory(classRegistrationHistoryRequest);

        log.info("Add Class Registration History Response successfully: {}", classRegistrationHistoryResponse);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Add Class Registration History successfully")
                .data(classRegistrationHistoryResponse)
                .build();
    }
}
