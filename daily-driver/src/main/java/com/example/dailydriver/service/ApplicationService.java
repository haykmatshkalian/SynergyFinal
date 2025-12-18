package com.example.dailydriver.service;

import com.example.dailydriver.entity.*;
import com.example.dailydriver.event.ApplicationRejectedEvent;
import com.example.dailydriver.exception.BusinessException;
import com.example.dailydriver.repository.ApplicationRepository;
import com.example.dailydriver.repository.CitizenRepository;
import com.example.dailydriver.repository.ProgramRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CitizenRepository citizenRepository;
    private final ProgramRepository programRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final HouseholdService householdService;

    public ApplicationService(ApplicationRepository applicationRepository,
                              CitizenRepository citizenRepository,
                              ProgramRepository programRepository,
                              ApplicationEventPublisher eventPublisher,
                              HouseholdService householdService) {
        this.applicationRepository = applicationRepository;
        this.citizenRepository = citizenRepository;
        this.programRepository = programRepository;
        this.eventPublisher = eventPublisher;
        this.householdService = householdService;
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

        if (existing.isPresent()) {
            Application app = existing.get();

            if (app.getStatus() == ApplicationStatus.DRAFT) {
                return app;
            }

            throw new BusinessException("Application already exists for this program");
        }

        Application application = new Application();
        application.setCitizen(citizen);
        application.setProgram(program);
        application.setIsDraft(isDraft);
        application.setSubmissionDate(OffsetDateTime.now());

        if (isDraft) {
            application.setStatus(ApplicationStatus.DRAFT);
            return applicationRepository.save(application);
        }

        validateEligibility(citizen, program);

        application.setStatus(ApplicationStatus.SUBMITTED);
        return applicationRepository.save(application);
    }

    public Application finalSubmit(Long applicationId) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException("Application not found"));

        if (application.getStatus() != ApplicationStatus.DRAFT) {
            throw new BusinessException("Only draft applications can be submitted");
        }

        validateEligibility(application.getCitizen(), application.getProgram());

        application.setIsDraft(false);
        application.setStatus(ApplicationStatus.SUBMITTED);

        return applicationRepository.save(application);
    }

    public Application changeStatus(Long applicationId,
                                    ApplicationStatus newStatus,
                                    String permissions) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException("Application not found"));

        ApplicationStatus current = application.getStatus();

        if (newStatus == ApplicationStatus.REVIEW &&
                current == ApplicationStatus.SUBMITTED) {

            application.setStatus(ApplicationStatus.REVIEW);
        }
        else if ((newStatus == ApplicationStatus.APPROVED ||
                newStatus == ApplicationStatus.REJECTED)
                && current == ApplicationStatus.REVIEW) {

            if (permissions == null || !permissions.contains("APPLICATION:APPROVE")) {
                throw new BusinessException("Permission denied to approve/reject application");
            }

            application.setStatus(newStatus);

            if (newStatus == ApplicationStatus.REJECTED) {
                eventPublisher.publishEvent(
                        new ApplicationRejectedEvent(application)
                );
            }
        }
        else {
            throw new BusinessException("Invalid status transition");
        }

        return applicationRepository.save(application);
    }


    private void validateEligibility(Citizen citizen, AssistanceProgram program) {

        int age = calculateAge(citizen.getDateOfBirth());

        if (program.getMinAge() != null && age < program.getMinAge()) {
            throw new BusinessException("Citizen is too young for this program");
        }

        if (program.getMaxAge() != null && age > program.getMaxAge()) {
            throw new BusinessException("Citizen exceeds maximum age for this program");
        }

        BigDecimal income = householdService.calculateHouseholdIncome(citizen);

        if (program.getMaxIncomeThreshold() != null &&
                income.compareTo(program.getMaxIncomeThreshold()) >= 0) {
            throw new BusinessException("Household income exceeds allowed threshold");
        }
    }

    public BigDecimal calculateHouseholdIncome(Citizen citizen) {

        Household household = citizen.getHousehold();

        if (household == null ||
                household.getMembers() == null ||
                household.getMembers().isEmpty()) {

            return citizen.getAnnualIncome();
        }

        return household.getMembers()
                .stream()
                .map(Citizen::getAnnualIncome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    private int calculateAge(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
