package org.example.backend.repository;

import org.example.backend.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByStudentId(String studentId);

    @Query("SELECT s FROM Student s WHERE s.studentId LIKE %:keyword% OR s.fullName LIKE %:keyword% OR s.email LIKE %:keyword% OR s.faculty.facultyName LIKE %:keyword%")
    Page<Student> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
