package com.example.OrbitOnboarding.service;

import com.example.OrbitOnboarding.dto.response.ModuleCompletionResponse;
import com.example.OrbitOnboarding.dto.response.MyProgressSummary;
import com.example.OrbitOnboarding.dto.response.ProgressSummaryResponse;
import com.example.OrbitOnboarding.dto.response.TrainingProgressResponse;
import com.example.OrbitOnboarding.entity.ModuleProgress;
import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.exception.DuplicateResource;
import com.example.OrbitOnboarding.exception.ResourceNotFoundException;
import com.example.OrbitOnboarding.repository.ModuleProgressRepository;
import com.example.OrbitOnboarding.repository.UserRepository;
import lombok.Builder;
import org.springframework.transaction.annotation.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Service
@Builder
@RequiredArgsConstructor
public class ModuleProgressService {

    private final TrainingModuleRepository moduleRepository;
    private final ModuleProgressRepository progressRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        System.out.println("SPRING SECURITY USERNAME = " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found"));
        System.out.println("DB USER FOUND ID = " + user.getId());
        return user;

    }
    /**
     * @param moduleId Controller moves to this service layer where it operates like
     *                 - logged-in user
     *                 - fetches module by id
     *                 - checks if already completed
     *                 - saves progress
     *                 - calculates progress
     *                 - builds and returns DTO
     *
     *
     *
     * @return
     */
    @Transactional
    public ModuleCompletionResponse markModuleComplete(Long moduleId) {

        User user = getCurrentUser();
        TrainingModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module Not Found")); //PRD requires a single module to be marked completed once
        progressRepository.findByUserAndModule(user, module).ifPresent(p -> {
            throw new DuplicateResource("Module Already completed"); //check once before finalizing
        });

        ModuleProgress progress = ModuleProgress.builder()
                .user(user)
                .module(module)
                .completed(true)
                .completedAt(LocalDateTime.now())
                .build();

        progressRepository.save(progress);

        long totalModules = moduleRepository.count();
        long completedModules = progressRepository.countByUserAndCompletedTrue(user);
        double percentage = ((double) completedModules / totalModules) * 100;
        return ModuleCompletionResponse.builder()
                .message("Module marked as complete")
                .moduleId(module.getId())
                .moduleTitle(module.getTitle())
                .completedAt(progress.getCompletedAt())
                .progressPercentage(percentage)
                .build();

    }
    /**
     * @return Controller moves to this service layer where it operates like
               - Gets logged-in user
               - Returns the total module count
               - Returns total no. of completed module
               - Calculate percentage
               - Building the required DTO
     */
    @Transactional(readOnly = true)
    public MyProgressSummary getMyProgress() {

        User user = getCurrentUser();
        long totalModules = moduleRepository.count();
        long completedModule = progressRepository.countByUserAndCompletedTrue(user);
        double percentage;
        try {
            percentage = (completedModule * 100.0) / totalModules;
        } catch (ArithmeticException ex) {
            percentage = 0;
        }
        ProgressSummaryResponse summary =
                new ProgressSummaryResponse(
                        totalModules,
                        completedModule,
                        percentage
                );
        MyProgressSummary.UserInfo userInfo =
                new MyProgressSummary.UserInfo(
                        user.getUsername(),
                        user.getFullName()
                );
        List<TrainingProgressResponse> moduleDetails =
                progressRepository.findByUser(user)
                        .stream()
                        .map(progress -> TrainingProgressResponse.builder()
                                .moduleId(progress.getModule().getId())
                                .moduleTitle(progress.getModule().getTitle())
                                .completed(progress.isCompleted())
                                .completedAt(progress.getCompletedAt())
                                .build())
                        .toList();
        return MyProgressSummary.builder()
                .user(userInfo)
                .summary(summary)
                .moduleDetails(moduleDetails)
                .build();


    }
    /**
     * @return Controller moves here to the service layer
               - Gets Logged-in user
               - Builds the required DTO

     */
    @Transactional(readOnly = true)
    public List<TrainingProgressResponse> getCompletedModules() {

        User user = getCurrentUser();
        return progressRepository.findByUser(user)
                .stream()
                .map(progress -> TrainingProgressResponse.builder()
                        .moduleId(progress.getModule().getId())
                        .moduleTitle(progress.getModule().getTitle())
                        .completed(progress.isCompleted())
                        .completedAt(progress.getCompletedAt())
                        .build())
                .toList();
    }
}
