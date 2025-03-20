package org.example.backend.service;

import org.example.backend.domain.SinhVien;
import org.example.backend.dto.request.SinhVienRequest;
import org.example.backend.dto.response.SinhVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISinhVienService {

    SinhVienResponse addStudent(SinhVienRequest request);

    SinhVienResponse getStudent(String mssv);

    SinhVienResponse updateStudent(String mssv, SinhVienRequest request);

    Page<SinhVienResponse> getAllStudent(Pageable pageable);

    void deleteStudent(String mssv);

    Page<SinhVienResponse> searchStudent(String keyword, Pageable pageable);
}
