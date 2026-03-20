package com.example.OrbitOnboarding.service;

import com.example.OrbitOnboarding.dto.response.ProgressSummaryResponse;
import com.example.OrbitOnboarding.dto.response.TrainingProgressResponse;
import com.example.OrbitOnboarding.entity.ModuleProgress;
import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.exception.DuplicateResource;
import com.example.OrbitOnboarding.exception.ResourceNotFoundException;
import com.example.OrbitOnboarding.repository.ModuleProgressRepository;
import com.example.OrbitOnboarding.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class ModuleProgressService {

    private final TrainingModuleRepository moduleRepository;
    private final ModuleProgressRepository progressRepository;
    private final UserRepository userRepository;


    //logged in User
    private User getCurrentUser(){
        String username= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not Found"));

    }


    public void markModuleComplete(Long moduleId){

        User user = getCurrentUser();

        TrainingModule module = moduleRepository.findById(moduleId)
                .orElseThrow(()-> new ResourceNotFoundException("Module Not Found"));

//not allowing duplicate completion
        //PRD requires a single module to be marked completed on
        progressRepository.findByUserAndModule(user,module).ifPresent(p -> {
            throw new DuplicateResource("Module Already completed"); //check once before finalizing
        });

        ModuleProgress progress= ModuleProgress.builder()
                .user(user)
                .module(module)
                .completed(true)
                .completedAt(LocalDateTime.now())
                .build();

        progressRepository.save(progress);

    }

    //Progress Summary Percentage
    //
    public ProgressSummaryResponse getMyProgress(){
        User user = getCurrentUser();

        int totalModules = (int)moduleRepository.count();

        int completedModule = (int)progressRepository.countByUserAndCompletedTrue(user);

        double percentage = totalModules == 0 ? 0 : (completedModule * 100.0)/totalModules;

        return new ProgressSummaryResponse(
                totalModules,
                completedModule,
                percentage
        );


    }

    //List Completed Modules
    //
    public List<TrainingProgressResponse> getCompletedModules(){

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
