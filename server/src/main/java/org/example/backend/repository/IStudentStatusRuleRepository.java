package org.example.backend.repository;

import org.example.backend.domain.StudentStatusRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentStatusRuleRepository extends JpaRepository<StudentStatusRule, Integer> {
}
