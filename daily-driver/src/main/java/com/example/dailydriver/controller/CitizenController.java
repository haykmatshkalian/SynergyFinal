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
    public ResponseEntity<CitizenDto> create(@RequestBody Citizen citizen) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CitizenDto(citizenService.create(citizen)));
    }


    @GetMapping
    public ResponseEntity<List<CitizenDto>> getAll() {
        return ResponseEntity.ok(
                citizenService.findAll()
                        .stream()
                        .map(CitizenDto::new)
                        .toList()
        );
    }


    @GetMapping("/search")
    public ResponseEntity<CitizenDto> searchByNationalId(
            @RequestParam String nationalId
    ) {
        return ResponseEntity.ok(
                new CitizenDto(citizenService.findByNationalId(nationalId))
        );
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<CitizenDto>> searchByName(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(
                citizenService.searchByName(name)
                        .stream()
                        .map(CitizenDto::new)
                        .toList()
        );
    }


    @GetMapping("/{id:\\d+}")
    public ResponseEntity<CitizenDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new CitizenDto(citizenService.findById(id))
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<CitizenDto> update(
            @PathVariable Long id,
            @RequestBody Citizen citizen
    ) {
        return ResponseEntity.ok(
                new CitizenDto(citizenService.update(id, citizen))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        citizenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

