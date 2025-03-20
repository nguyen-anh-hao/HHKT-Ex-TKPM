package org.example.backend.repository;

import org.example.backend.domain.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiaChiRepository extends JpaRepository<DiaChi, Integer> {
}
