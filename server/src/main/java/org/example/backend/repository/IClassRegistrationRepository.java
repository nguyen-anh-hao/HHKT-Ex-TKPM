package org.example.backend.repository;

import org.example.backend.common.RegistrationStatus;
import org.example.backend.domain.ClassRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClassRegistrationRepository extends JpaRepository<ClassRegistration, Integer> {

    @Query("""
                        SELECT COUNT(cr) > 0
                        FROM ClassRegistration cr
                        WHERE cr.aClass.id IN :classIds
                        AND cr.status = :status
    """)
    Boolean existsActiveRegistrationsByClassIds(@Param("classIds") List<Integer> classIds, @Param("status") RegistrationStatus status);
}
