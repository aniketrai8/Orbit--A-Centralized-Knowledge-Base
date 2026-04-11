package com.example.OrbitOnboarding.controller;

import com.example.OrbitOnboarding.dto.request.TrainingModuleRequest;
import com.example.OrbitOnboarding.dto.response.TrainingModuleResponse;
import com.example.OrbitOnboarding.unit.TrainingModuleService;
import io.swagger.v3.oas.annotations.Operation;
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


    /**
     * @param request Uses @RequestBody to read through the content and then passed down to the service layer
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Helps Create a new Training Module",description = "Only for ADMIN")
    public ResponseEntity<TrainingModuleResponse> createModule(@RequestBody TrainingModuleRequest request){
        return ResponseEntity.ok(service.createModule(request));
    }

    /**
     * @return Controller redirects to the service layer method responsible to fetch and return all the modules
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Returns all existing Modules",description = "")
    public ResponseEntity<List<TrainingModuleResponse>> getAllModules(){
        return ResponseEntity.ok(service.getAllModules());
    }

    /**
     * @param id Uses @PathVariable to fetch the requested module ID from path and pass it to service layer
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Returns the requested Modules",description = "")
    public ResponseEntity<TrainingModuleResponse> getAllModuleById(@PathVariable Long id){
        return ResponseEntity.ok(service.getModuleById(id));
    }

    /**
     * @param id uses @PathVariable to fetch the desired module id from path
     * @param request uses @RequestBody to read through the request body and pass it down the service layer
     * @return
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Updates the existing Module",description = "Only for ADMIN")
    public ResponseEntity<TrainingModuleResponse> updateModule(@PathVariable Long id, @RequestBody TrainingModuleRequest request) {
        return ResponseEntity.ok(service.updateModule(id, request));
    }

    /**
     * @param id Uses @PathVariable to extract the desired id from the path and pass it down to service layer
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletes an existing Module",description = "Only for ADMIN")
    public ResponseEntity<String> deleteModule(@PathVariable Long id) {

        service.deleteModule(id);
        return ResponseEntity.ok("Training module deleted successfully");
    }



}
