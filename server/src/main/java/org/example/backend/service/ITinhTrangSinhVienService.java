package org.example.backend.service;

import org.example.backend.dto.request.TinhTrangSinhVienRequest;
import org.example.backend.dto.response.TinhTrangSinhVienResponse;

public interface ITinhTrangSinhVienService {
    TinhTrangSinhVienResponse addTinhTrangSinhVien(TinhTrangSinhVienRequest request);
}
