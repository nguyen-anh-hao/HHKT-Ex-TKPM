package org.example.backend.repository;

import org.example.backend.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Integer> {

    boolean existsByCourseName(String courseName);
}
