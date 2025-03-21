package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.request.ChuongTrinhRequest;
import org.example.backend.dto.response.ApiResponse;
import org.example.backend.dto.response.ChuongTrinhResponse;
import org.example.backend.service.IChuongTrinhService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chuong-trinh")
@RequiredArgsConstructor
public class ChuongTrinhController {
    private final IChuongTrinhService chuongTrinhService;

    @PostMapping("")
    public ApiResponse addChuongTrinh(@RequestBody @Valid ChuongTrinhRequest request) {
        ChuongTrinhResponse chuongTrinh = chuongTrinhService.addChuongTrinh(request);

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(chuongTrinh)
                .build();
    }
}
