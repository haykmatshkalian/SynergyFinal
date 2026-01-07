package com.example.dailydriver.service;

import com.example.dailydriver.entity.*;
import com.example.dailydriver.exception.BusinessException;
import com.example.dailydriver.repository.ApplicationRepository;
import com.example.dailydriver.repository.CitizenRepository;
import com.example.dailydriver.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CitizenRepository citizenRepository;
    private final ProgramRepository programRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            CitizenRepository citizenRepository,
            ProgramRepository programRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.citizenRepository = citizenRepository;
        this.programRepository = programRepository;
    }

    public Application submit(Long citizenId, Long programId, boolean isDraft) {

        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new BusinessException("Citizen not found"));

        AssistanceProgram program = programRepository.findById(programId)
                .orElseThrow(() -> new BusinessException("Program not found"));

        Optional<Application> existing =
                applicationRepository.findByCitizen_CitizenIdAndProgram_ProgramId(
                        citizenId, programId
                );

        Application application = existing.orElseGet(Application::new);

        application.setCitizen(citizen);
        application.setProgram(program);
        application.setSubmissionDate(OffsetDateTime.now());

        if (isDraft) {
            application.setStatus(ApplicationStatus.DRAFT);
            application.setIsDraft(true);
        } else {
            application.setStatus(ApplicationStatus.SUBMITTED);
            application.setIsDraft(false);
        }

        return applicationRepository.save(application);
    }

    public Application finalSubmit(Long id) {
        Application application = findById(id);
        application.setStatus(ApplicationStatus.SUBMITTED);
        application.setIsDraft(false);
        return applicationRepository.save(application);
    }

    public Application changeStatus(Long id, ApplicationStatus status) {
        Application application = findById(id);
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    public List<Application> findAll() {
        return applicationRepository.findAll(); // 🔴 THIS IS THE KEY LINE
    }

    public Application findById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Application not found"));
    }

    public List<Application> findByCitizen(Long citizenId) {
        return applicationRepository.findByCitizen_CitizenId(citizenId);
    }

    public List<Application> findByProgram(Long programId) {
        return applicationRepository.findByProgram_ProgramId(programId);
    }

    public void delete(Long id) {
        applicationRepository.deleteById(id);
    }
}

