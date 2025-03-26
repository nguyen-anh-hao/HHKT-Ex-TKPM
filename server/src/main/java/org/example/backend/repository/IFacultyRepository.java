package org.example.backend.repository;

import org.example.backend.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFacultyRepository extends JpaRepository<Faculty, Integer> {
    Optional<Faculty> findByFacultyName(String facultyName);
}
