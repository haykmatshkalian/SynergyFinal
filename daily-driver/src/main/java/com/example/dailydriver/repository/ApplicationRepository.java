package com.example.dailydriver.repository;

import com.example.dailydriver.entity.Application;
import com.example.dailydriver.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByCitizen_CitizenIdAndProgram_ProgramId(
            Long citizenId,
            Long programId
    );

    List<Application> findByCitizen_CitizenId(Long citizenId);

    List<Application> findByProgram_ProgramId(Long programId);

    List<Application> findByStatus(ApplicationStatus status);
}

