package com.example.dailydriver.controller;

import com.example.dailydriver.entity.Citizen;
import com.example.dailydriver.service.CitizenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/citizens")
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping
    public ResponseEntity<Citizen> create(@RequestBody Citizen citizen) {
        Citizen created = citizenService.create(citizen);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll(
            @RequestHeader(value = "X-PERMISSIONS", required = false) String permissions
    ) {
        boolean canViewIncome =
                permissions != null && permissions.contains("CITIZEN:VIEW_SENSITIVE");

        List<Map<String, Object>> result = citizenService.findAll().stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("citizenId", c.getCitizenId());
            map.put("fullName", c.getFullName());
            map.put("nationalId", c.getNationalId());
            map.put("annualIncome",
                    canViewIncome ? c.getAnnualIncome() : "*****");
            return map;
        }).toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Citizen> getById(@PathVariable Long id) {
        return ResponseEntity.ok(citizenService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Citizen> searchByNationalId(@RequestParam String nationalId) {
        return ResponseEntity.ok(citizenService.findByNationalId(nationalId));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Citizen>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(citizenService.searchByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Citizen> update(
            @PathVariable Long id,
            @RequestBody Citizen citizen
    ) {
        return ResponseEntity.ok(citizenService.update(id, citizen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        citizenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
