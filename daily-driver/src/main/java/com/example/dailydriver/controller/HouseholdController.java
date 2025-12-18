package com.example.dailydriver.controller;

import com.example.dailydriver.dto.HouseholdDto;
import com.example.dailydriver.entity.Household;
import com.example.dailydriver.service.HouseholdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @PostMapping
    public ResponseEntity<Household> create() {
        Household household = householdService.create();
        return ResponseEntity.status(HttpStatus.CREATED).body(household);
    }

    @PostMapping("/{householdId}/citizens/{citizenId}")
    public ResponseEntity<HouseholdDto> addCitizen(
            @PathVariable Long householdId,
            @PathVariable Long citizenId) {

        householdService.addCitizen(householdId, citizenId);
        return ResponseEntity.ok(householdService.get(householdId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseholdDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.get(id));
    }
}
