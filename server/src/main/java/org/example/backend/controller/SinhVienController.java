package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
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
public class SinhVienController {

    private final ISinhVienService sinhVienService;

    @PostMapping("")
    public ApiResponse addStudent(@RequestBody @Valid SinhVienRequest request) {

        SinhVienResponse sinhVien = sinhVienService.addStudent(request);

        return ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Success")
                .data(sinhVien)
                .build();
    }

    @GetMapping("/{mssv}")
    public ApiResponse getStudent(@PathVariable String mssv) {
        SinhVienResponse sinhVien = sinhVienService.getStudent(mssv);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sinhVien)
                .build();
    }

    @GetMapping("")
    public ApiResponse getAllStudent(@PageableDefault(size = 3, page = 0) Pageable pageable) {
        Page<SinhVienResponse> sinhVienPage = sinhVienService.getAllStudent(pageable);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sinhVienPage.getContent())
                .paginationInfo(new PaginationInfo(sinhVienPage))
                .build();
    }

    @PutMapping("/{mssv}")
    public ApiResponse updateStudent(@PathVariable String mssv, @RequestBody @Valid SinhVienRequest request) {
        SinhVienResponse sinhVien = sinhVienService.updateStudent(mssv, request);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sinhVien)
                .build();
    }

    @DeleteMapping("/{mssv}")
    public ApiResponse deleteStudent(@PathVariable String mssv) {
        sinhVienService.deleteStudent(mssv);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse  searchStudents(@RequestParam String keyword, @PageableDefault(size = 3, page = 0) Pageable pageable) {
        Page<SinhVienResponse> sinhVienPage = sinhVienService.searchStudent(keyword, pageable);

        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(sinhVienPage.getContent())
                .paginationInfo(new PaginationInfo(sinhVienPage))
                .build();
    }

}
