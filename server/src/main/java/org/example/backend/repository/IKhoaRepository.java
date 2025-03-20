package org.example.backend.repository;

import org.example.backend.domain.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKhoaRepository extends JpaRepository<Khoa, Integer> {
}
