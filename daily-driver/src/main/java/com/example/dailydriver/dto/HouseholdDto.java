package com.example.dailydriver.dto;

import java.util.List;

public class HouseholdDto {

    private Long id;
    private List<Long> memberIds;

    public HouseholdDto(Long id, List<Long> memberIds) {
        this.id = id;
        this.memberIds = memberIds;
    }

    public Long getId() {
        return id;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }
}
