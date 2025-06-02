package org.example.backend.repository;

import org.example.backend.domain.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILecturerRepository extends JpaRepository<Lecturer, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
