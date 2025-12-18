package com.example.dailydriver.service;

import com.example.dailydriver.entity.Citizen;
import com.example.dailydriver.exception.BusinessException;
import com.example.dailydriver.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;

    public CitizenService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }


    public Citizen create(Citizen citizen) {
        return citizenRepository.save(citizen);
    }


    public List<Citizen> findAll() {
        return citizenRepository.findAll();
    }

    public Citizen findById(Long id) {
        return citizenRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Citizen not found"));
    }

    public Citizen findByNationalId(String nationalId) {
        return citizenRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new BusinessException("Citizen not found"));
    }


    public List<Citizen> searchByName(String name) {
        return citizenRepository.findByFullNameContainingIgnoreCase(name);
    }

    public Citizen update(Long id, Citizen updated) {

        Citizen existing = findById(id);

        existing.setFullName(updated.getFullName());
        existing.setNationalId(updated.getNationalId());
        existing.setDateOfBirth(updated.getDateOfBirth());
        existing.setCity(updated.getCity());
        existing.setAddress(updated.getAddress());
        existing.setAnnualIncome(updated.getAnnualIncome());
        existing.setHousehold(updated.getHousehold()); // linking allowed, logic elsewhere

        return citizenRepository.save(existing);
    }

    public void delete(Long id) {
        citizenRepository.deleteById(id);
    }
}
