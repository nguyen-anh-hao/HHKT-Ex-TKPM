package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TinhTrangSinhVienController {
    private final ITinhTrangSinhVienService tinhTrangSinhVienService;

    @PostMapping("")
    public ApiResponse addTinhTrangSinhVien(@RequestBody @Valid TinhTrangSinhVienRequest request) {
        log.info("Received request to add tinh trang sinh vien: {}", request.getTenTinhTrang());

        TinhTrangSinhVienResponse tinhTrangSinhVien = tinhTrangSinhVienService.addTinhTrangSinhVien(request);

        log.info("Successfully added tinh trang sinh vien: {}", tinhTrangSinhVien.getTenTinhTrang());

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(tinhTrangSinhVien)
                .build();
    }
}
