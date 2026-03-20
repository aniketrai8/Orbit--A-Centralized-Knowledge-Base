package com.example.OrbitOnboarding.service;


import com.example.OrbitOnboarding.dto.request.TrainingModuleRequest;
import com.example.OrbitOnboarding.dto.response.TrainingModuleResponse;
import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.exception.ResourceNotFoundException;
import com.example.OrbitOnboarding.mapper.TrainingModuleMapper;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class TrainingModuleServiceTest {

    @Mock
    private TrainingModuleRepository repository;

    @Mock
    private TrainingModuleMapper mapper;

    @InjectMocks
    private TrainingModuleService service;

    @Test
    void shouldCreateModule() {

        TrainingModuleRequest request = new TrainingModuleRequest();
        TrainingModule entity = new TrainingModule();
        TrainingModuleResponse response = new TrainingModuleResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        TrainingModuleResponse result = service.createModule(request);

        assertEquals(response, result);
    }

    @Test
    void shouldReturnAllModules() {

        TrainingModule module = new TrainingModule();
        TrainingModuleResponse response = new TrainingModuleResponse();

        when(repository.findAll()).thenReturn(List.of(module));
        when(mapper.toResponse(module)).thenReturn(response);

        List<TrainingModuleResponse> result = service.getAllModules();

        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowIfModuleNotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getModuleById(1L));
    }

    @Test
    void shouldDeleteModule() {

        when(repository.existsById(1L)).thenReturn(true);

        service.deleteModule(1L);

        verify(repository).deleteById(1L);
    }
}
