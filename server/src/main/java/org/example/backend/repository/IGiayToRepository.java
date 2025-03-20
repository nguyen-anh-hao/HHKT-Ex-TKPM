package org.example.backend.repository;

import org.example.backend.domain.GiayTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGiayToRepository extends JpaRepository<GiayTo, Integer> {

}
