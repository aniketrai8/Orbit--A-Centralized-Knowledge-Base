package com.example.OrbitOnboarding.service;


import com.example.OrbitOnboarding.dto.request.TrainingModuleRequest;
import com.example.OrbitOnboarding.dto.response.TrainingModuleDelete;
import com.example.OrbitOnboarding.dto.response.TrainingModuleResponse;
import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.exception.ResourceNotFoundException;
import com.example.OrbitOnboarding.mapper.TrainingModuleMapper;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;
import com.example.OrbitOnboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingModuleService {

    private final TrainingModuleRepository trainingRepository;
    private final TrainingModuleMapper trainingMapper;
    private final UserRepository userRepository;

    @Transactional
    public TrainingModuleResponse createModule(TrainingModuleRequest request){


        TrainingModule module = trainingMapper.toEntity(request);
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        module.setCreatedBy(creator);
        TrainingModule saved = trainingRepository.save(module);
        return trainingMapper.toResponse(saved);

    }

    @Transactional(readOnly = true)
    public List<TrainingModuleResponse> getAllModules() {

        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrainingModuleResponse getModuleById(Long id) {

        TrainingModule module = trainingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training module not found"));

        return trainingMapper.toResponse(module);
    }

    @Transactional
    public TrainingModuleResponse updateModule(Long id, TrainingModuleRequest request){

        TrainingModule module = trainingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training Module not found"));
        module.setTitle(request.getTitle());
        module.setDescription(request.getDescription());
        module.setUpdatedAt(LocalDateTime.now());
        TrainingModule updated = trainingRepository.save(module);
        return trainingMapper.toResponse(updated);
    }

    @Transactional
    public TrainingModuleDelete deleteModule(Long id){

        TrainingModule module = trainingRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Article Not Found"));

        TrainingModuleDelete response = new TrainingModuleDelete(
                module.getId(),
                module.getTitle(),
                module.getDescription(),
                "Module Deleted Successfully"
        );

        trainingRepository.delete(module);
        return response;

    }



}






