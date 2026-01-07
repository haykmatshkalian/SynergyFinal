package com.example.dailydriver.dto;

import com.example.dailydriver.entity.AssistanceProgram;

import java.math.BigDecimal;

public class ProgramDto {

    public Long program_id;
    public String program_name;
    public Boolean is_active;
    public Integer min_age;
    public Integer max_age;
    public BigDecimal max_income_threshold;

    public ProgramDto(AssistanceProgram p) {
        this.program_id = p.getProgramId();
        this.program_name = p.getProgramName();
        this.is_active = p.getIsActive();
        this.min_age = p.getMinAge();
        this.max_age = p.getMaxAge();
        this.max_income_threshold = p.getMaxIncomeThreshold();
    }
}

