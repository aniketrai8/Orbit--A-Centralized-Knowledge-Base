package com.example.OrbitOnboarding.integration;

import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProgressModuleIntegrationTest extends BaseIntegrationTest {

    @Autowired
    TrainingModuleRepository moduleRepository;

    @Test
    void shouldRequireAuthenticationForProgress() throws Exception {

        // create module
        TrainingModule module = new TrainingModule();
        module.setTitle("Spring Boot");
        module.setDescription("Valid description text");
        module.setContent("This is valid module content long enough for validation.");
        module.setModuleOrder(1);
        module.setEstimatedHour(5);

        module = moduleRepository.save(module);

        // no JWT → should fail
        mockMvc.perform(post("/api/progress/" + module.getId()))
                .andExpect(status().isForbidden());
    }

    //shouldMarkModuleAsCompleted
    //shouldNotAllowDuplicateCompletion
    //shouldCalculatePercentageCorrectly
    //shouldReturnUserProgress
    //shouldAllowAdminToViewAllProgress
}