package com.example.dailydriver.repository;

import com.example.dailydriver.entity.Citizen;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {


    @EntityGraph(attributePaths = { "household", "household.members" })
    List<Citizen> findAll();

    Optional<Citizen> findByNationalId(String nationalId);

    List<Citizen> findByFullNameContainingIgnoreCase(String name);

}
