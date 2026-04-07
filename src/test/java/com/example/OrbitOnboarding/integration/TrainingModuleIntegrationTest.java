package com.example.OrbitOnboarding.integration;

import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrainingModuleIntegrationTest extends BaseIntegrationTest {

    @Autowired
    TrainingModuleRepository repository;

    @Test
    void shouldCreateTrainingModule() throws Exception {

        String json = """
        {
          "title":"Spring Boot Basics",
          "description":"Learn fundamentals of Spring Boot",
          "content":"This module explains Spring Boot architecture and setup in detail.",
          "moduleOrder":1,
          "estimatedHour":5
        }
        """;

        // secured endpoint → should reject without auth
        mockMvc.perform(post("/api/training/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetAllModules() throws Exception {

        TrainingModule module = new TrainingModule();
        module.setTitle("Test Module");
        module.setDescription("description long enough");
        module.setContent("content content content content content content, This is a detailed training module content used only for \" +\n" +
                "    \"integration testing purposes and must exceed fifty characters.");
        module.setModuleOrder(1);
        module.setEstimatedHour(2);

        repository.save(module);

        mockMvc.perform(get("/api/training/module"))
                .andExpect(status().isForbidden());
    }
}