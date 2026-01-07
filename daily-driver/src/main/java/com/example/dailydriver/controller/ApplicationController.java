package com.example.dailydriver.controller;

import com.example.dailydriver.dto.ApplicationResponseDto;
import com.example.dailydriver.entity.Application;
import com.example.dailydriver.entity.ApplicationStatus;
import com.example.dailydriver.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDto> submit(
            @RequestParam Long citizenId,
            @RequestParam Long programId,
            @RequestParam(defaultValue = "false") boolean isDraft
    ) {
        Application application =
                applicationService.submit(citizenId, programId, isDraft);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApplicationResponseDto(application));
    }

    @PostMapping("/{id}/final-submit")
    public ResponseEntity<ApplicationResponseDto> finalSubmit(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApplicationResponseDto(
                        applicationService.finalSubmit(id)
                )
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponseDto> changeStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status
    ) {
        return ResponseEntity.ok(
                new ApplicationResponseDto(
                        applicationService.changeStatus(id, status)
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDto>> getAll() {
        return ResponseEntity.ok(
                applicationService.findAll()
                        .stream()
                        .map(ApplicationResponseDto::new)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApplicationResponseDto(
                        applicationService.findById(id)
                )
        );
    }

    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<ApplicationResponseDto>> getByCitizen(
            @PathVariable Long citizenId
    ) {
        return ResponseEntity.ok(
                applicationService.findByCitizen(citizenId)
                        .stream()
                        .map(ApplicationResponseDto::new)
                        .toList()
        );
    }

    @GetMapping("/program/{programId}")
    public ResponseEntity<List<ApplicationResponseDto>> getByProgram(
            @PathVariable Long programId
    ) {
        return ResponseEntity.ok(
                applicationService.findByProgram(programId)
                        .stream()
                        .map(ApplicationResponseDto::new)
                        .toList()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
