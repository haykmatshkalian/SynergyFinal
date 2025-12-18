package com.example.dailydriver.service;

import com.example.dailydriver.dto.HouseholdDto;
import com.example.dailydriver.entity.Citizen;
import com.example.dailydriver.entity.Household;
import com.example.dailydriver.exception.BusinessException;
import com.example.dailydriver.repository.CitizenRepository;
import com.example.dailydriver.repository.HouseholdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

@Service
@Transactional
public class HouseholdService {

    private final HouseholdRepository householdRepository;
    private final CitizenRepository citizenRepository;

    public HouseholdService(HouseholdRepository householdRepository,
                            CitizenRepository citizenRepository) {
        this.householdRepository = householdRepository;
        this.citizenRepository = citizenRepository;
    }

    public Household create() {
        return householdRepository.save(new Household());
    }

    public void addCitizen(Long householdId, Long citizenId) {

        Household household = householdRepository.findById(householdId)
                .orElseThrow(() -> new BusinessException("Household not found"));

        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new BusinessException("Citizen not found"));

        household.addMember(citizen);
    }

    public Household getEntity(Long id) {
        return householdRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Household not found"));
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


    public HouseholdDto get(Long id) {
        Household household = getEntity(id);

        return new HouseholdDto(
                household.getId(),
                household.getMembers()
                        .stream()
                        .map(Citizen::getCitizenId)
                        .toList()
        );
    }
}
