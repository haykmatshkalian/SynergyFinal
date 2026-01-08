package com.example.dailydriver.dto;

import com.example.dailydriver.entity.Citizen;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CitizenDto {

    public Long citizen_id;
    public String full_name;
    public String national_id;
    public LocalDate date_of_birth;
    public String city;
    public String address;
    public BigDecimal annual_income;

    public Long household_id;

    public CitizenDto(Citizen citizen) {
        this.citizen_id = citizen.getCitizenId();
        this.full_name = citizen.getFullName();
        this.national_id = citizen.getNationalId();
        this.date_of_birth = citizen.getDateOfBirth();
        this.city = citizen.getCity();
        this.address = citizen.getAddress();
        this.annual_income = citizen.getAnnualIncome();

        this.household_id = citizen.getHousehold() != null
                ? citizen.getHousehold().getId()
                : null;
    }
}