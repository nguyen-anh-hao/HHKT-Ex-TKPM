package org.example.backend.controller;

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
    public APIResponse getAllEmailDomains() {
        log.info("Received request to get all email domains");

        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(emailDomainService.getAllDomains())
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse getEmailDomainById(@PathVariable Integer id) {
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
    public APIResponse updateEmailDomain(@PathVariable Integer id, @RequestBody @Valid EmailDomainRequest request) {
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
    public APIResponse deleteEmailDomain(@PathVariable Integer id) {
        log.info("Received request to delete email domain with id: {}", id);

        emailDomainService.deleteDomain(id);

        log.info("Successfully deleted email domain with id: {}", id);
        return APIResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }
}
