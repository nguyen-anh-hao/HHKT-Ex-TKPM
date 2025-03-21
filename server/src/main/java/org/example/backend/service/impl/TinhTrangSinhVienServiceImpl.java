package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.TinhTrangSinhVien;
import org.example.backend.dto.request.TinhTrangSinhVienRequest;
import org.example.backend.dto.response.TinhTrangSinhVienResponse;
import org.example.backend.mapper.TinhTrangSinhVienMapper;
import org.example.backend.repository.ITinhTrangRepository;
import org.example.backend.service.ITinhTrangSinhVienService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TinhTrangSinhVienServiceImpl implements ITinhTrangSinhVienService {
    private final ITinhTrangRepository tinhTrangRepository;

    @Override
    public TinhTrangSinhVienResponse addTinhTrangSinhVien(TinhTrangSinhVienRequest request) {
        if (tinhTrangRepository.findByTenTinhTrang(request.getTenTinhTrang()).isPresent()) {
            log.error("Tinh trang sinh vien already exists");
            throw new RuntimeException("Tinh trang sinh vien already exists");
        }

        TinhTrangSinhVien tinhTrangSinhVien = TinhTrangSinhVienMapper.toDomainFromRequestDTO(request);
        tinhTrangSinhVien = tinhTrangRepository.save(tinhTrangSinhVien);

        log.info("Tinh trang sinh vien saved to database successfully");

        return TinhTrangSinhVienMapper.toResponseDTO(tinhTrangSinhVien);
    }
}
