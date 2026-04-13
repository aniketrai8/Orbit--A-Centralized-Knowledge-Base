package com.example.OrbitOnboarding.controller;

import com.example.OrbitOnboarding.dto.response.ModuleCompletionResponse;
import com.example.OrbitOnboarding.dto.response.MyProgressSummary;
import com.example.OrbitOnboarding.dto.response.TrainingProgressResponse;
import com.example.OrbitOnboarding.service.ModuleProgressService;
import io.swagger.v3.oas.annotations.Operation;
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

    /**
     * @param moduleId Uses @PathVariable to extract the id from the path passes it to the service layer and then passed as HTTP response
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/complete/{moduleId}")
    @Operation(summary = "Responsible for flagging a module as completed",description = "")
    public ResponseEntity<ModuleCompletionResponse> complete(@PathVariable Long moduleId) {
        return ResponseEntity.ok(moduleProgressService.markModuleComplete(moduleId));
    }

    /**
     * @return Controller redirects to service layer gets required information and return json response
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/my-progress")
    @Operation(summary = "Returns the progress of the logged user",description = "")
    public ResponseEntity<MyProgressSummary> getMyProgress() {
        return ResponseEntity.ok(moduleProgressService.getMyProgress()); //
    }

    /**
     * @return Controller layer shifts to desired service layer method that fetches all completed modules
     */
    @GetMapping("/completed")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Returns information on completed Modules",description = "Only for ADMIN")
    public ResponseEntity<List<TrainingProgressResponse>> getCompletedModules() {

        return ResponseEntity.ok(moduleProgressService.getCompletedModules()); //
    }


}