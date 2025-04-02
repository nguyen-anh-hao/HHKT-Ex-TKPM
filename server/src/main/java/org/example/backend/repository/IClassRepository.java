package org.example.backend.repository;

import org.example.backend.domain.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClassRepository extends JpaRepository<Class, Integer> {
    Optional<List<Class>> findAllByCourseId (Integer courseId);

    Boolean existsByCourseId(Integer courseId);
}
