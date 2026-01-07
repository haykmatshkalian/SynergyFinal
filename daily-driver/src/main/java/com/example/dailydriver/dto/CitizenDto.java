package com.example.dailydriver.dto;

import com.example.dailydriver.entity.Citizen;

public class CitizenDto {

    public Long citizen_id;
    public String full_name;
    public String national_id;
    public String date_of_birth;
    public String address;
    public Object annual_income;

    public CitizenDto(Citizen c, boolean maskIncome) {
        this.citizen_id = c.getCitizenId();
        this.full_name = c.getFullName();
        this.national_id = c.getNationalId();
        this.date_of_birth = c.getDateOfBirth().toString();
        this.address = c.getAddress();
        this.annual_income = maskIncome ? "*****" : c.getAnnualIncome();
    }
}

