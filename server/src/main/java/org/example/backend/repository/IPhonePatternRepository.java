package org.example.backend.repository;

import org.example.backend.domain.PhonePattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhonePatternRepository extends JpaRepository<PhonePattern, String> {
}