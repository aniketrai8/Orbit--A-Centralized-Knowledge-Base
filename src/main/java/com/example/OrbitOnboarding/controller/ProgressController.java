package com.example.OrbitOnboarding.controller;

import com.example.OrbitOnboarding.dto.response.ModuleCompletionResponse;
import com.example.OrbitOnboarding.dto.response.MyProgressSummary;
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
    public ResponseEntity<ModuleCompletionResponse> complete(@PathVariable Long moduleId) {

        ModuleCompletionResponse response =
                moduleProgressService.markModuleComplete(moduleId);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/my-progress")
    public ResponseEntity<MyProgressSummary> getMyProgress() {

        return ResponseEntity.ok(moduleProgressService.getMyProgress()); //
    }

    @GetMapping("/completed")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TrainingProgressResponse> getCompletedModules() {

        return moduleProgressService.getCompletedModules(); //
    }


}