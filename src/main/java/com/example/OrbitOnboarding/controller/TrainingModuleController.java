package com.example.OrbitOnboarding.controller;

import com.example.OrbitOnboarding.dto.request.TrainingModuleRequest;
import com.example.OrbitOnboarding.dto.response.TrainingModuleResponse;
import com.example.OrbitOnboarding.service.TrainingModuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="Training Module APIs",description="Handles CRUD operation and search feature")
@RestController
@RequestMapping("/api/training/module")
@RequiredArgsConstructor

public class TrainingModuleController {

    private final TrainingModuleService service;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrainingModuleResponse> createModule(@RequestBody TrainingModuleRequest request){
        return ResponseEntity.ok(service.createModule(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<TrainingModuleResponse>> getAllModules(){
        return ResponseEntity.ok(service.getAllModules());


    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<TrainingModuleResponse> getAllModuleById(@PathVariable Long id){
        return ResponseEntity.ok(service.getModuleById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrainingModuleResponse> updateModule(
            @PathVariable Long id,
            @RequestBody TrainingModuleRequest request) {

        return ResponseEntity.ok(service.updateModule(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteModule(@PathVariable Long id) {

        service.deleteModule(id);
        return ResponseEntity.ok("Training module deleted successfully");
    }



}
