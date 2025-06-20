package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping("")
    @Operation(summary = "Add a class registration history", description = "Add a new class registration history record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class registration history created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClassRegistrationHistoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
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
