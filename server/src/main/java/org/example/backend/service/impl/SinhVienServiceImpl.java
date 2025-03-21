package org.example.backend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.ChuongTrinh;
import org.example.backend.domain.DiaChi;
import org.example.backend.domain.GiayTo;
import org.example.backend.domain.Khoa;
import org.example.backend.domain.SinhVien;
import org.example.backend.domain.TinhTrangSinhVien;
import org.example.backend.dto.request.SinhVienRequest;
import org.example.backend.dto.response.SinhVienResponse;
import org.example.backend.mapper.DiaChiMapper;
import org.example.backend.mapper.GiayToMapper;
import org.example.backend.mapper.SinhVienMapper;
import org.example.backend.repository.IChuongTrinhRepository;
import org.example.backend.repository.IKhoaRepository;
import org.example.backend.repository.ISinhVienRepository;
import org.example.backend.repository.ITinhTrangRepository;
import org.example.backend.service.ISinhVienService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SinhVienServiceImpl implements ISinhVienService {

    private final ISinhVienRepository sinhVienRepository;
    private final IChuongTrinhRepository chuongTrinhRepository;
    private final IKhoaRepository khoaRepository;
    private final ITinhTrangRepository tinhTrangRepository;

    @Override
    @Transactional
    public List<SinhVienResponse> addStudents(List<SinhVienRequest> requests) {
        return requests.stream()
                .map(this::addStudent)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SinhVienResponse addStudent(SinhVienRequest request) {
        Khoa khoa = khoaRepository.findById(request.getKhoaId()).orElseThrow(() -> new RuntimeException("Khoa not found"));
        ChuongTrinh chuongTrinh = chuongTrinhRepository.findById(request.getChuongTrinhId()).orElseThrow(() -> new RuntimeException("Chuong trinh not found"));
        TinhTrangSinhVien tinhTrang = tinhTrangRepository.findById(request.getTinhTrangId()).orElseThrow(() -> new RuntimeException("Tinh trang not found"));

        SinhVien sinhVien = SinhVienMapper.toDomainFromRequestDTO(request);
        sinhVien.setKhoa(khoa);
        sinhVien.setChuongTrinh(chuongTrinh);
        sinhVien.setTinhTrang(tinhTrang);

        sinhVien = sinhVienRepository.save(sinhVien);

        log.info("SinhVien {} has been created and saved", sinhVien.getMssv());

        return SinhVienMapper.toResponseDTO(sinhVien);
    }

    @Override
    public SinhVienResponse getStudent(String mssv) {
        SinhVien sinhVien = sinhVienRepository.findByMssv(mssv).orElseThrow(() -> new RuntimeException("Sinh vien not found"));

        log.info("SinhVien {} has been retrieved", sinhVien.getMssv());

        return SinhVienMapper.toResponseDTO(sinhVien);
    }

    @Override
    public Page<SinhVienResponse> getAllStudent(Pageable pageable) {
        return sinhVienRepository.findAll(pageable).map(SinhVienMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public SinhVienResponse updateStudent(String mssv, SinhVienRequest request) {
        SinhVien sinhVien = sinhVienRepository.findByMssv(mssv).orElseThrow(() -> new RuntimeException("Sinh vien not found"));
        Khoa khoa = khoaRepository.findById(request.getKhoaId()).orElseThrow(() -> new RuntimeException("Khoa not found"));
        ChuongTrinh chuongTrinh = chuongTrinhRepository.findById(request.getChuongTrinhId()).orElseThrow(() -> new RuntimeException("Chuong trinh not found"));
        TinhTrangSinhVien tinhTrang = tinhTrangRepository.findById(request.getTinhTrangId()).orElseThrow(() -> new RuntimeException("Tinh trang not found"));

        sinhVien.setHoTen(request.getHoTen());
        sinhVien.setNgaySinh(request.getNgaySinh());
        sinhVien.setGioiTinh(request.getGioiTinh());
        sinhVien.setKhoaHoc(request.getKhoaHoc());
        sinhVien.setEmail(request.getEmail());
        sinhVien.setSoDienThoai(request.getSoDienThoai());
        sinhVien.setQuocTich(request.getQuocTich());
        sinhVien.setKhoa(khoa);
        sinhVien.setChuongTrinh(chuongTrinh);
        sinhVien.setTinhTrang(tinhTrang);

        log.info("SinhVien {} has been updated all fields except DiaChi and GiayTo", sinhVien.getMssv());

        // Update DiaChi (Remove only addresses that are no longer in request)
        List<DiaChi> existingDiaChis = sinhVien.getDiaChis();
        List<DiaChi> updatedDiaChis = request.getDiaChis().stream()
                .map(diaChiRequest -> {
                    DiaChi diaChi = DiaChiMapper.toDomain(diaChiRequest);
                    diaChi.setSinhVien(sinhVien);
                    return diaChi;
                })
                .collect(Collectors.toList());

        existingDiaChis.clear();
        existingDiaChis.addAll(updatedDiaChis);

        log.info("DiaChi of SinhVien {} has been updated", sinhVien.getMssv());

        // Update GiayTo (Update existing or add new)
        List<GiayTo> existingGiayTos = sinhVien.getGiayTos();
        List<GiayTo> updatedGiayTos = request.getGiayTos().stream()
                .map(giayToRequest -> {
                    return existingGiayTos.stream()
                            .filter(existing -> existing.getSoGiayTo().equals(giayToRequest.getSoGiayTo())) // Check if it already exists
                            .findFirst()
                            .map(existing -> { // Update existing
                                existing.setLoaiGiayTo(giayToRequest.getLoaiGiayTo());
                                existing.setNoiCap(giayToRequest.getNoiCap());
                                existing.setNgayCap(giayToRequest.getNgayCap());
                                existing.setNgayHetHan(giayToRequest.getNgayHetHan());
                                existing.setQuocGiaCap(giayToRequest.getQuocGiaCap());
                                existing.setGhiChu(giayToRequest.getGhiChu());
                                existing.setCoGanChip(giayToRequest.getCoGanChip());
                                return existing;
                            })
                            .orElseGet(() -> { // Create new if not exists
                                GiayTo giayTo = GiayToMapper.toDomain(giayToRequest);
                                giayTo.setSinhVien(sinhVien);
                                return giayTo;
                            });
                })
                .collect(Collectors.toList());

        existingGiayTos.clear();
        existingGiayTos.addAll(updatedGiayTos);

        log.info("GiayTo of SinhVien {} has been updated", sinhVien.getMssv());

        SinhVien updatedSinhVien = sinhVienRepository.save(sinhVien);

        log.info("SinhVien {} has been updated all fields and saved", sinhVien.getMssv());

        return SinhVienMapper.toResponseDTO(updatedSinhVien);
    }

    @Override
    public void deleteStudent(String mssv) {
        SinhVien sinhVien = sinhVienRepository.findByMssv(mssv).orElseThrow(() -> new RuntimeException("Sinh vien not found"));
        sinhVienRepository.delete(sinhVien);

        log.info("SinhVien {} has been deleted", sinhVien.getMssv());
    }

    @Override
    public Page<SinhVienResponse> searchStudent(String keyword, Pageable pageable) {
        return sinhVienRepository.searchByKeyword(keyword, pageable).map(SinhVienMapper::toResponseDTO);
    }
}
