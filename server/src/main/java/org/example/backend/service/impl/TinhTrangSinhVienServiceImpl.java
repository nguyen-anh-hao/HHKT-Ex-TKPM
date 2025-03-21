package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.TinhTrangSinhVien;
import org.example.backend.dto.request.TinhTrangSinhVienRequest;
import org.example.backend.dto.response.TinhTrangSinhVienResponse;
import org.example.backend.mapper.TinhTrangSinhVienMapper;
import org.example.backend.repository.ITinhTrangRepository;
import org.example.backend.service.ITinhTrangSinhVienService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TinhTrangSinhVienServiceImpl implements ITinhTrangSinhVienService {
    private final ITinhTrangRepository tinhTrangRepository;

    @Override
    public TinhTrangSinhVienResponse addTinhTrangSinhVien(TinhTrangSinhVienRequest request) {
        if (tinhTrangRepository.findByTenTinhTrang(request.getTenTinhTrang()).isPresent()) {
            throw new RuntimeException("Tinh trang sinh vien already exists");
        }

        TinhTrangSinhVien tinhTrangSinhVien = TinhTrangSinhVienMapper.toDomainFromRequestDTO(request);
        tinhTrangSinhVien = tinhTrangRepository.save(tinhTrangSinhVien);

        return TinhTrangSinhVienMapper.toResponseDTO(tinhTrangSinhVien);
    }
}
