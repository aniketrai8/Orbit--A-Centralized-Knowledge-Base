package com.example.OrbitOnboarding.service;


import com.example.OrbitOnboarding.dto.request.TrainingModuleRequest;
import com.example.OrbitOnboarding.dto.response.TrainingModuleResponse;
import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.exception.ResourceNotFoundException;
import com.example.OrbitOnboarding.mapper.TrainingModuleMapper;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingModuleService {

    private final TrainingModuleRepository repository;
    private final TrainingModuleMapper mapper;

    @Transactional
    public TrainingModuleResponse createModule(TrainingModuleRequest request){

        TrainingModule module = mapper.toEntity(request);
        TrainingModule saved = repository.save(module);
        return mapper.toResponse(saved);

    }

    @Transactional(readOnly = true)
    public List<TrainingModuleResponse> getAllModules() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrainingModuleResponse getModuleById(Long id) {

        TrainingModule module = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training module not found"));

        return mapper.toResponse(module);
    }

    @Transactional
    public TrainingModuleResponse updateModule(Long id, TrainingModuleRequest request){

        TrainingModule module = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training Module not found"));
        module.setTitle(request.getTitle());
        module.setDescription(request.getDescription());
        TrainingModule updated = repository.save(module);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void deleteModule(Long id){

        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Training Module does not exist");
        }

        repository.deleteById(id);
    }



}






