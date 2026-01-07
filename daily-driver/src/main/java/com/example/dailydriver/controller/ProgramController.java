package com.example.dailydriver.controller;

import com.example.dailydriver.dto.ProgramDto;
import com.example.dailydriver.entity.AssistanceProgram;
import com.example.dailydriver.service.ProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    public ResponseEntity<ProgramDto> create(@RequestBody AssistanceProgram program) {
        AssistanceProgram created = programService.create(program);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ProgramDto(created));
    }

    @GetMapping
    public ResponseEntity<List<ProgramDto>> getAll() {
        List<ProgramDto> result = programService.findAll()
                .stream()
                .map(ProgramDto::new)
                .toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDto> getById(@PathVariable Long id) {
        AssistanceProgram program = programService.findById(id);
        return ResponseEntity.ok(new ProgramDto(program));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramDto> update(
            @PathVariable Long id,
            @RequestBody AssistanceProgram program
    ) {
        AssistanceProgram updated = programService.update(id, program);
        return ResponseEntity.ok(new ProgramDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        programService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
