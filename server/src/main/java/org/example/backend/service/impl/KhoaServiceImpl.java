package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.Khoa;
import org.example.backend.dto.request.KhoaRequest;
import org.example.backend.dto.response.KhoaResponse;
import org.example.backend.mapper.KhoaMapper;
import org.example.backend.repository.IKhoaRepository;
import org.example.backend.service.IKhoaService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KhoaServiceImpl implements IKhoaService {
    private final IKhoaRepository khoaRepository;

    @Override
    @Transactional
    public KhoaResponse addKhoa(KhoaRequest request) {
        if (khoaRepository.findByTenKhoa(request.getTenKhoa()).isPresent()) {
            log.error("Khoa already exists");
            throw new RuntimeException("Khoa already exists");
        }

        Khoa khoa = KhoaMapper.toDomainFromRequestDTO(request);
        khoa = khoaRepository.save(khoa);

        log.info("Khoa saved to database successfully");

        return KhoaMapper.toResponseDTO(khoa);
    }
}
