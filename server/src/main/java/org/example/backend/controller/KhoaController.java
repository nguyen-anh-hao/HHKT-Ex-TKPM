package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dto.request.KhoaRequest;
import org.example.backend.dto.response.ApiResponse;
import org.example.backend.dto.response.KhoaResponse;
import org.example.backend.service.IKhoaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khoa")
@RequiredArgsConstructor
@Slf4j
public class KhoaController {
    private final IKhoaService khoaService;

    @PostMapping("")
    public ApiResponse addKhoa(@RequestBody @Valid KhoaRequest request) {
        log.info("Received request to add khoa: {}", request.getTenKhoa());

        KhoaResponse khoa = khoaService.addKhoa(request);

        log.info("Successfully added khoa: {}", khoa.getTenKhoa());

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(khoa)
                .build();
    }
}
