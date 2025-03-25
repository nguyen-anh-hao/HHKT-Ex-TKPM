package org.example.backend.repository;

import org.example.backend.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProgramRepository extends JpaRepository<Program, Integer> {
    Optional<Program> findByProgramName(String programName);
}
