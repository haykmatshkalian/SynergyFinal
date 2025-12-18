package com.example.dailydriver.controller;

import com.example.dailydriver.entity.Application;
import com.example.dailydriver.entity.ApplicationStatus;
import com.example.dailydriver.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Application> submit(
            @RequestParam Long citizenId,
            @RequestParam Long programId,
            @RequestParam(defaultValue = "false") boolean isDraft
    ) {
        Application application =
                applicationService.submit(citizenId, programId, isDraft);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(application);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> changeStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status,
            @RequestHeader(value = "X-PERMISSIONS", required = false) String permissions
    ) {
        Application updated =
                applicationService.changeStatus(id, status, permissions);

        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/final-submit")
    public ResponseEntity<Application> finalSubmit(@PathVariable Long id) {
        Application submitted = applicationService.finalSubmit(id);
        return ResponseEntity.ok(submitted);
    }
}
