package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.ChuongTrinh;
import org.example.backend.dto.request.ChuongTrinhRequest;
import org.example.backend.dto.response.ChuongTrinhResponse;
import org.example.backend.mapper.ChuongTrinhMapper;
import org.example.backend.repository.IChuongTrinhRepository;
import org.example.backend.service.IChuongTrinhService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChuongTrinhServiceImpl implements IChuongTrinhService {
    private final IChuongTrinhRepository chuongTrinhRepository;

    @Override
    public ChuongTrinhResponse addChuongTrinh(ChuongTrinhRequest request) {
        if (chuongTrinhRepository.findByTenChuongTrinh(request.getTenChuongTrinh()).isPresent()) {
            log.error("Chuong trinh already exists");
            throw new RuntimeException("Chuong trinh already exists");
        }

        ChuongTrinh chuongTrinh = ChuongTrinhMapper.toDomainFromRequestDTO(request);
        chuongTrinh = chuongTrinhRepository.save(chuongTrinh);

        log.info("Chuong trinh saved to database successfully");

        return ChuongTrinhMapper.toResponseDTO(chuongTrinh);
    }
}
