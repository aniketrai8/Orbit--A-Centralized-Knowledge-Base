package com.example.OrbitOnboarding.unit;


import com.example.OrbitOnboarding.dto.request.TrainingModuleRequest;
import com.example.OrbitOnboarding.dto.response.TrainingModuleDelete;
import com.example.OrbitOnboarding.dto.response.TrainingModuleResponse;
import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.exception.ResourceNotFoundException;
import com.example.OrbitOnboarding.mapper.TrainingModuleMapper;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;
import com.example.OrbitOnboarding.repository.UserRepository;
import com.example.OrbitOnboarding.service.TrainingModuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class TrainingModuleServiceTest {

    @Mock
    private TrainingModuleRepository trainingRepository;

    @Mock
    private TrainingModuleMapper trainingMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrainingModuleService TrainingService;

    @Test
    void shouldCreateModule() {

        TrainingModuleRequest request = new TrainingModuleRequest();
        TrainingModule entity = new TrainingModule();
        TrainingModuleResponse response = new TrainingModuleResponse();
        User user = new User();

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("admin");
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));
        when(trainingMapper.toEntity(request)).thenReturn(entity);
        when(trainingRepository.save(entity)).thenReturn(entity);
        when(trainingMapper.toResponse(entity)).thenReturn(response);

        TrainingModuleResponse result = TrainingService.createModule(request);

        assertEquals(response, result);
    }

    @Test
    void shouldReturnAllModules() {

        TrainingModule module = new TrainingModule();
        TrainingModuleResponse response = new TrainingModuleResponse();

        when(trainingRepository.findAll()).thenReturn(List.of(module));
        when(trainingMapper.toResponse(module)).thenReturn(response);

        List<TrainingModuleResponse> result = TrainingService.getAllModules();

        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowIfModuleNotFound() {

        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> TrainingService.getModuleById(1L));
    }

    @Test
    void shouldDeleteModule() {

        TrainingModule module = new TrainingModule();
        module.setId(1L);
        module.setTitle("Java Basics");
        module.setDescription("Core Java training module");

        when(trainingRepository.findById(1L)).thenReturn(Optional.of(module));

        TrainingModuleDelete response = TrainingService.deleteModule(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Java Basics", response.getTitle());
        assertEquals("Core Java training module", response.getDescription());

        verify(trainingRepository).delete(module);

    }
}
