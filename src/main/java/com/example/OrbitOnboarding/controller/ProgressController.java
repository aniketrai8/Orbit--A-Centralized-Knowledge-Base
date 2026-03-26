package com.example.OrbitOnboarding.controller;

import com.example.OrbitOnboarding.dto.response.ProgressSummaryResponse;
import com.example.OrbitOnboarding.dto.response.TrainingProgressResponse;
import com.example.OrbitOnboarding.service.ModuleProgressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="Progress Status API",description = "Showcases Progress Percentage of Modules")
@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ModuleProgressService moduleProgressService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/complete/{moduleId}")
    public ResponseEntity<?> complete(@PathVariable Long moduleId) {

        moduleProgressService.markModuleComplete(moduleId); //

        return ResponseEntity.ok("Module marked as completed");
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/my-progress")
    public ProgressSummaryResponse getMyProgress() {

        return moduleProgressService.getMyProgress(); //
    }

    @GetMapping("/completed")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TrainingProgressResponse> getCompletedModules() {

        return moduleProgressService.getCompletedModules(); //
    }
}