package org.example.backend.repository;

import org.example.backend.domain.ClassRegistrationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassRegistrationHistoryRepository extends JpaRepository<ClassRegistrationHistory, Integer> {

}
