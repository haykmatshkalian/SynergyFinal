package com.example.dailydriver.controller;

import com.example.dailydriver.dto.CitizenDto;
import com.example.dailydriver.entity.Citizen;
import com.example.dailydriver.service.CitizenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CitizenDto>> getAll(
            @RequestHeader(value = "X-PERMISSIONS", required = false) String permissions
    ) {
        boolean canViewIncome =
                permissions != null && permissions.contains("CITIZEN:VIEW_SENSITIVE");

        List<CitizenDto> result = citizenService.findAll()
                .stream()
                .map(c -> new CitizenDto(c, !canViewIncome))
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenDto> getById(
            @PathVariable Long id,
            @RequestHeader(value = "X-PERMISSIONS", required = false) String permissions
    ) {
        boolean canViewIncome =
                permissions != null && permissions.contains("CITIZEN:VIEW_SENSITIVE");

        Citizen citizen = citizenService.findById(id);
        return ResponseEntity.ok(new CitizenDto(citizen, !canViewIncome));
    }

    @GetMapping("/search")
    public ResponseEntity<CitizenDto> searchByNationalId(
            @RequestParam String nationalId,
            @RequestHeader(value = "X-PERMISSIONS", required = false) String permissions
    ) {
        boolean canViewIncome =
                permissions != null && permissions.contains("CITIZEN:VIEW_SENSITIVE");

        Citizen citizen = citizenService.findByNationalId(nationalId);
        return ResponseEntity.ok(new CitizenDto(citizen, !canViewIncome));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<CitizenDto>> searchByName(
            @RequestParam String name,
            @RequestHeader(value = "X-PERMISSIONS", required = false) String permissions
    ) {
        boolean canViewIncome =
                permissions != null && permissions.contains("CITIZEN:VIEW_SENSITIVE");

        List<CitizenDto> result = citizenService.searchByName(name)
                .stream()
                .map(c -> new CitizenDto(c, !canViewIncome))
                .toList();

        return ResponseEntity.ok(result);
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
