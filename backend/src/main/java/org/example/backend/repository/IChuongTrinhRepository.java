package org.example.backend.repository;

import org.example.backend.domain.ChuongTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChuongTrinhRepository extends JpaRepository<ChuongTrinh, Integer> {
}
