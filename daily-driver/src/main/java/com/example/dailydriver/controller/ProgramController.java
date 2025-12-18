package com.example.dailydriver.controller;

import com.example.dailydriver.entity.AssistanceProgram;
import com.example.dailydriver.service.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    public ResponseEntity<AssistanceProgram> create(@RequestBody AssistanceProgram program) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(programService.create(program));
    }

    @GetMapping
    public ResponseEntity<List<AssistanceProgram>> getAll() {
        return ResponseEntity.ok(programService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssistanceProgram> getById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssistanceProgram> update(
            @PathVariable Long id,
            @RequestBody AssistanceProgram program
    ) {
        return ResponseEntity.ok(programService.update(id, program));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        programService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
