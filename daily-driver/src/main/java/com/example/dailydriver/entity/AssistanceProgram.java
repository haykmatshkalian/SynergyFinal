package com.example.dailydriver.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "assistance_program")
public class AssistanceProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programId;

    @Column(name = "program_name", nullable = false)
    private String programName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "max_income_threshold")
    private BigDecimal maxIncomeThreshold;


    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public BigDecimal getMaxIncomeThreshold() {
        return maxIncomeThreshold;
    }

    public void setMaxIncomeThreshold(BigDecimal maxIncomeThreshold) {
        this.maxIncomeThreshold = maxIncomeThreshold;
    }
}
