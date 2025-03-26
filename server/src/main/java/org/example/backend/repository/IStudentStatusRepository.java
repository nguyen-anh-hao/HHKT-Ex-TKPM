package org.example.backend.repository;

import org.example.backend.domain.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStudentStatusRepository extends JpaRepository<StudentStatus, Integer> {
    Optional<StudentStatus> findByStudentStatusName(String studentStatusName);
}
