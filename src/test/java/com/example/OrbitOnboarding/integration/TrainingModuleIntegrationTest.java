package com.example.OrbitOnboarding.integration;

import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

class TrainingModuleIntegrationTest extends BaseIntegrationTest {

    @Autowired
    TrainingModuleRepository repository;

    private String unique(String base) {
        return base + "_" + System.currentTimeMillis();
    }

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

        // secured endpoint -> should reject without auth
        mockMvc.perform(post("/api/training/module")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)) //
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetAllModules() throws Exception {

        TrainingModule module = new TrainingModule();
        module.setTitle(unique("Test Module"));
        module.setDescription("description long enough");
        module.setContent("content content content content content content, This is a detailed training module content used only for \" +\n" +
                "    \"integration testing purposes and must exceed fifty characters.");
        module.setModuleOrder((int)(System.currentTimeMillis() % 10000));
        module.setEstimatedHour(2);

        repository.save(module);

        mockMvc.perform(get("/api/training/module"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetModuleById() throws Exception{

        TrainingModule module = new TrainingModule();
        module.setTitle(unique("Module One"));
        module.setDescription("description long enough");
        module.setContent("content content content content content content, This is a detailed training module content used only for \\\" +\\n\" +\n" +
                "                \"    \\\"integration testing purposes and must exceed fifty characters.");
        module.setModuleOrder((int)(System.currentTimeMillis() % 10000));
        module.setEstimatedHour(8);

        TrainingModule saved = repository.save(module);

        mockMvc.perform(get("/api/training/module/" + saved.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldUpdateTrainingModule() throws Exception{

        TrainingModule module= new TrainingModule();

        module.setTitle(unique("Old Title"));
        module.setDescription("description long enough");
        module.setContent("content content content content content content, This is a detailed training module content used only for \\\" +\\n\" +\n" +
                "                \"    \\\"integration testing purposes and must exceed fifty characters");
        module.setModuleOrder((int)(System.currentTimeMillis() % 10000));
        module.setEstimatedHour(11);

        TrainingModule saved = repository.save(module);


        String updateJson = """
        {
          "title":"Updated Title",
          "description":"updated description long enough",
          "content":"content content content content content content, This is a detailed training module content used only for \\\\\\" +\\\\n\\" +\\n" +
                                             "                \\"    \\\\\\"integration testing purposes and must exceed fifty characters",
          "moduleOrder":3,
          "estimatedHour":4
        }
        """;

        mockMvc.perform(put("/api/training/module/"+ saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isForbidden());

    }

    @Test
    void shouldDeleteTrainingModule() throws Exception{

        TrainingModule module = new TrainingModule();
        module.setTitle(unique("Delete Module"));
        module.setDescription("description long enough");
        module.setContent("content content content content content content, This is a detailed training module content used only for \\\\\\\" +\\\\n\\\" +\\n\" +\n" +
                "                \"                \\\"    \\\\\\\"integration testing purposes and must exceed fifty characters");

        module.setModuleOrder((int)(System.currentTimeMillis() % 10000));
        module.setEstimatedHour(3);

        TrainingModule saved = repository.save(module);

        mockMvc.perform(delete("/api/training/module/" +saved.getId()))
                .andExpect(status().isForbidden());

    }

    @Test
    void shouldPreventDuplicateModuleOrder() throws Exception {

        TrainingModule module = new TrainingModule();
        module.setTitle("Module already exists");
        module.setDescription("description long enough");
        module.setContent("content content content content content content, This is a detailed training module content used only for \\\\\\\\\\\\\\\" +\\\\\\\\n\\\\\\\" +\\\\n\\\" +\\n\" +\n" +
                "                \"                \\\"                \\\\\\\"    \\\\\\\\\\\\\\\"integration testing purposes and must exceed fifty characters");
        int order = (int) (System.currentTimeMillis() % 10000);
        module.setModuleOrder(order);
        module.setEstimatedHour(12);
        repository.save(module);


        String duplicateJson = """
        {
          "title":"%s",
          "description":"another description long enough",
          "content":"duplicate duplicate duplicate duplicate duplicate duplicate duplicate duplicate",
          "moduleOrder":%d,
          "estimatedHour":3
        }
        """.formatted(unique("Duplicate Moudle order"),order);

        mockMvc.perform(post("/api/training/module/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(duplicateJson))
                .andExpect(status().isBadRequest());

    }


}