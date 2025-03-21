package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.request.TinhTrangSinhVienRequest;
import org.example.backend.dto.response.ApiResponse;
import org.example.backend.dto.response.TinhTrangSinhVienResponse;
import org.example.backend.service.ITinhTrangSinhVienService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tinh-trang")
@RequiredArgsConstructor
public class TinhTrangSinhVienController {
    private final ITinhTrangSinhVienService tinhTrangSinhVienService;

    @PostMapping("")
    public ApiResponse addTinhTrangSinhVien(@RequestBody @Valid TinhTrangSinhVienRequest request) {
        TinhTrangSinhVienResponse tinhTrangSinhVien = tinhTrangSinhVienService.addTinhTrangSinhVien(request);

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(tinhTrangSinhVien)
                .build();
    }
}
