package org.example.backend.repository;

import org.example.backend.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISemesterRepository extends JpaRepository<Semester, Integer> {
    Optional<Semester> findBySemesterAndAcademicYear(Integer semester, String academicYear);
}
