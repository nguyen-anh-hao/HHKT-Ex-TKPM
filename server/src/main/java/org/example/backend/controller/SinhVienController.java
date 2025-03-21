package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.common.PaginationInfo;
import org.example.backend.dto.request.SinhVienRequest;
import org.example.backend.dto.response.ApiResponse;
import org.example.backend.dto.response.SinhVienResponse;
import org.example.backend.service.ISinhVienService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sinh-vien")
@RequiredArgsConstructor
@Slf4j
public class SinhVienController {

    private final ISinhVienService sinhVienService;

    @PostMapping("")
    public ApiResponse addStudent(@RequestBody @Valid SinhVienRequest request) {
        log.info("Received request to add student: {}", request.getHoTen());

        SinhVienResponse sinhVien = sinhVienService.addStudent(request);

        log.info("Successfully added student: {}", sinhVien.getHoTen());

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(sinhVien)
                .build();
    }

    @GetMapping("/{mssv}")
    public ApiResponse getStudent(@PathVariable String mssv) {
        log.info("Received request to get student with mssv: {}", mssv);

        SinhVienResponse sinhVien = sinhVienService.getStudent(mssv);

        log.info("Successfully retrieved student with mssv: {}", mssv);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sinhVien)
                .build();
    }

    @GetMapping("")
    public ApiResponse getAllStudent(@PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to get all students");

        Page<SinhVienResponse> sinhVienPage = sinhVienService.getAllStudent(pageable);

        log.info("Successfully retrieved all students");

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sinhVienPage.getContent())
                .paginationInfo(new PaginationInfo(sinhVienPage))
                .build();
    }

    @PutMapping("/{mssv}")
    public ApiResponse updateStudent(@PathVariable String mssv, @RequestBody @Valid SinhVienRequest request) {
        log.info("Received request to update student with mssv: {}", mssv);

        SinhVienResponse sinhVien = sinhVienService.updateStudent(mssv, request);

        log.info("Successfully updated student with mssv: {}", mssv);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sinhVien)
                .build();
    }

    @DeleteMapping("/{mssv}")
    public ApiResponse deleteStudent(@PathVariable String mssv) {
        log.info("Received request to delete student with mssv: {}", mssv);

        sinhVienService.deleteStudent(mssv);

        log.info("Successfully deleted student with mssv: {}", mssv);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse  searchStudents(@RequestParam String keyword, @PageableDefault(size = 3, page = 0) Pageable pageable) {
        log.info("Received request to search students with keyword: {}", keyword);

        Page<SinhVienResponse> sinhVienPage = sinhVienService.searchStudent(keyword, pageable);

        log.info("Successfully searched students with keyword: {}", keyword);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sinhVienPage.getContent())
                .paginationInfo(new PaginationInfo(sinhVienPage))
                .build();
    }

}
