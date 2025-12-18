package com.example.dailydriver.repository;

import com.example.dailydriver.entity.AssistanceProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository
        extends JpaRepository<AssistanceProgram, Long> {
}
