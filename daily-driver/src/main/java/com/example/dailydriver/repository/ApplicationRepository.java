package com.example.dailydriver.repository;

import com.example.dailydriver.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByCitizen_CitizenIdAndProgram_ProgramId(
            Long citizenId,
            Long programId
    );
}

