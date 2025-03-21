package org.example.backend.repository;

import org.example.backend.domain.TinhTrangSinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITinhTrangRepository extends JpaRepository<TinhTrangSinhVien, Integer> {
    Optional<TinhTrangSinhVien> findByTenTinhTrang(String tenTinhTrang);
}
