package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.ChuongTrinh;
import org.example.backend.dto.request.ChuongTrinhRequest;
import org.example.backend.dto.response.ChuongTrinhResponse;
import org.example.backend.mapper.ChuongTrinhMapper;
import org.example.backend.repository.IChuongTrinhRepository;
import org.example.backend.service.IChuongTrinhService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChuongTrinhServiceImpl implements IChuongTrinhService {
    private final IChuongTrinhRepository chuongTrinhRepository;

    @Override
    public ChuongTrinhResponse addChuongTrinh(ChuongTrinhRequest request) {
        if (chuongTrinhRepository.findByTenChuongTrinh(request.getTenChuongTrinh()).isPresent()) {
            throw new RuntimeException("Chuong trinh already exists");
        }

        ChuongTrinh chuongTrinh = ChuongTrinhMapper.toDomainFromRequestDTO(request);
        chuongTrinh = chuongTrinhRepository.save(chuongTrinh);

        return ChuongTrinhMapper.toResponseDTO(chuongTrinh);
    }
}
