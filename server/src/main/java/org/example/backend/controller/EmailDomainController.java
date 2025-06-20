package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.EmailDomainRequest;
import org.example.backend.dto.response.APIResponse;
import org.example.backend.dto.response.EmailDomainResponse;
import org.example.backend.service.IEmailDomainService;
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
@RequestMapping("/api/email-domains")
@RequiredArgsConstructor
@Slf4j
public class EmailDomainController {
    private final IEmailDomainService emailDomainService;

    @GetMapping("")
    @Operation(summary = "Get all email domains", description = "Retrieve a list of all email domains")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email domains retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmailDomainResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getAllEmailDomains() {
        log.info("Received request to get all email domains");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(emailDomainService.getAllDomains())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get email domain by ID", description = "Retrieve an email domain using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email domain retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailDomainResponse.class))),
            @ApiResponse(responseCode = "404", description = "Email domain not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse getEmailDomainById(
            @Parameter(description = "ID of the email domain to retrieve", example = "1")
            @PathVariable Integer id) {
        log.info("Received request to get email domain with id: {}", id);

        EmailDomainResponse emailDomain = emailDomainService.getDomainById(id);

        log.info("Successfully retrieved email domain with id: {}", id);
        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(emailDomain)
                .build();
    }

    @PostMapping("")
    @Operation(summary = "Add email domain", description = "Create a new email domain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Email domain created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailDomainResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse addEmailDomain(@RequestBody @Valid EmailDomainRequest request) {
        log.info("Received request to add email domain: {}", request.getDomain());

        EmailDomainResponse emailDomain = emailDomainService.createDomain(request);

        log.info("Successfully added email domain: {}", emailDomain.getDomain());
        return APIResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(emailDomain)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update email domain", description = "Update an existing email domain by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email domain updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmailDomainResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Email domain not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse updateEmailDomain(
            @Parameter(description = "ID of the email domain to update", required = true, example = "1")
            @PathVariable Integer id,

            @RequestBody @Valid EmailDomainRequest request) {
        log.info("Received request to update email domain with id: {}", id);

        EmailDomainResponse emailDomain = emailDomainService.updateDomain(id, request);

        log.info("Successfully updated email domain with id: {}", id);
        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(emailDomain)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete email domain", description = "Delete an existing email domain by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email domain deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Email domain not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    public APIResponse deleteEmailDomain(
            @Parameter(description = "ID of the email domain to delete", required = true, example = "1")
            @PathVariable Integer id) {
        log.info("Received request to delete email domain with id: {}", id);

        emailDomainService.deleteDomain(id);

        log.info("Successfully deleted email domain with id: {}", id);
        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
