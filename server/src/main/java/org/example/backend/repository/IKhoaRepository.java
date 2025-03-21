package org.example.backend.repository;

import org.example.backend.domain.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IKhoaRepository extends JpaRepository<Khoa, Integer> {
    Optional<Khoa> findByTenKhoa(String tenKhoa);
}
