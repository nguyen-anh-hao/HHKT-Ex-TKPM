package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.ClassRegistrationHistoryRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.ClassRegistrationHistoryResponse;
import org.example.backend.service.IClassRegistrationHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/class-registration-histories")
@RequiredArgsConstructor
public class ClassRegistrationHistoryController {
    private final IClassRegistrationHistoryService classRegistrationHistoryService;

    @GetMapping("/")
    public String get() {
        log.info("Received request to get Class Registration History");
        return "Hello World!";
    }

    @PostMapping("")
    public APIResponse addClassRegistrationHistory(@RequestBody @Valid ClassRegistrationHistoryRequest classRegistrationHistoryRequest) {

        log.info("Received request to add Class Registration History: {}", classRegistrationHistoryRequest);

        ClassRegistrationHistoryResponse classRegistrationHistoryResponse = classRegistrationHistoryService.addClassRegistrationHistory(classRegistrationHistoryRequest);

        log.info("Add Class Registration History Response successfully: {}", classRegistrationHistoryResponse);

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Add Class Registration History successfully")
                .data(classRegistrationHistoryResponse)
                .build();
    }
}
