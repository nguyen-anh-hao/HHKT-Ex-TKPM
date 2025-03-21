package org.example.backend.repository;

import org.example.backend.domain.ChuongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IChuongTrinhRepository extends JpaRepository<ChuongTrinh, Integer> {
    Optional<ChuongTrinh> findByTenChuongTrinh(String tenChuongTrinh);
}
