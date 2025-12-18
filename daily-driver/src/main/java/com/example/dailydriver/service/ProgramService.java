package com.example.dailydriver.service;

import com.example.dailydriver.entity.AssistanceProgram;
import com.example.dailydriver.exception.BusinessException;
import com.example.dailydriver.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public AssistanceProgram create(AssistanceProgram program) {
        return programRepository.save(program);
    }

    public List<AssistanceProgram> findAll() {
        return programRepository.findAll();
    }

    public AssistanceProgram findById(Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Program not found"));
    }

    public AssistanceProgram update(Long id, AssistanceProgram updated) {
        AssistanceProgram existing = findById(id);

        existing.setProgramName(updated.getProgramName());
        existing.setIsActive(updated.getIsActive());
        existing.setMinAge(updated.getMinAge());
        existing.setMaxAge(updated.getMaxAge());
        existing.setMaxIncomeThreshold(updated.getMaxIncomeThreshold());

        return programRepository.save(existing);
    }

    public void delete(Long id) {
        programRepository.deleteById(id);
    }
}
