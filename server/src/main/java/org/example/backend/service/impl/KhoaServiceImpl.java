package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.domain.Khoa;
import org.example.backend.dto.request.KhoaRequest;
import org.example.backend.dto.response.KhoaResponse;
import org.example.backend.mapper.KhoaMapper;
import org.example.backend.repository.IKhoaRepository;
import org.example.backend.service.IKhoaService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KhoaServiceImpl implements IKhoaService {
    private final IKhoaRepository khoaRepository;

    @Override
    @Transactional
    public KhoaResponse addKhoa(KhoaRequest request) {
        if (khoaRepository.findByTenKhoa(request.getTenKhoa()).isPresent()) {
            throw new RuntimeException("Khoa already exists");
        }

        Khoa khoa = KhoaMapper.toDomainFromRequestDTO(request);
        khoa = khoaRepository.save(khoa);

        return KhoaMapper.toResponseDTO(khoa);
    }
}
