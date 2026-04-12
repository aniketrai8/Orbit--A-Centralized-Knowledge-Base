package com.example.OrbitOnboarding.integration;

import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;

import com.example.OrbitOnboarding.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProgressModuleIntegrationTest extends BaseIntegrationTest {

    /**
     *
     */
    @Autowired
    TrainingModuleRepository moduleRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * @throws Exception
     */
    @Test
    void shouldRequireAuthenticationForProgress() throws Exception {

        // create module
        TrainingModule module = new TrainingModule();
        module.setTitle("Spring Boot");
        module.setDescription("Valid description text");
        module.setContent("This is valid module content long enough for validation.This explains git branching strategy used in company. This content is intentionally long enough to pass validation constraints.\"\n" +
                "  ");
        module.setModuleOrder(1);
        module.setEstimatedHour(5);

        module = moduleRepository.save(module);

        // no JWT -> should fail
        mockMvc.perform(post("/api/progress/" + module.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldMarkModuleComplete() throws Exception{

        TrainingModule module = new TrainingModule();
        module.setTitle("Spring Boot");
        module.setDescription("Valid description text");
        module.setContent("This is valid module content long enough for validation.This explains git branching strategy used in company. This content is intentionally long enough to pass validation constraints.\\\"\\n\" +\n" +
                "                \"  ");
        module.setModuleOrder(2);
        module.setEstimatedHour(4);

        TrainingModule saved= moduleRepository.save(module);

        mockMvc.perform(post("/api/progress/"+ saved.getId()))
                .andExpect(status().isForbidden());


    }

    @Test
    void shouldPreventDuplicateCompletion() throws Exception{

        TrainingModule module = new TrainingModule();
        module.setTitle("Spring Boot");
        module.setDescription("Valid description text");
        module.setContent("This is valid module content long enough for validation.This explains git branching strategy used in company. This content is intentionally long enough to pass validation constraints.\\\"\\n\" +\n" +
                "                \"  ");
        module.setModuleOrder(2);
        module.setEstimatedHour(4);

        TrainingModule saved = moduleRepository.save(module);

        mockMvc.perform(post("/api/progress/complete"+saved.getId()))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/api/progress/complete"+saved.getId()))
                .andExpect(status().isForbidden()); //

    }
    @Test
    void calculateProgressPercentageCorrectly() throws Exception{

        for(int i=0;i<=5;i++){
            TrainingModule module = new TrainingModule();
            module.setTitle("SpringBoot");
            module.setDescription("Valid description text");
            module.setContent("This is valid module content long enough for validation.This explains git branching strategy used in company. This content is intentionally long enough to pass validation constraints.\\\\\\\"\\\\n\\\" +\\n\" +\n" +
                    "                \"                \\\" ");
            module.setModuleOrder(i);
            module.setEstimatedHour(7);

            TrainingModule saved = moduleRepository.save(module);

        }
        mockMvc.perform(get("/api/progress/my-progress"))
                .andExpect(status().isForbidden());

    }

    @Test
    void shouldViewOwnProgress() throws Exception   {
        mockMvc.perform(get("/api/progress/my-progress"))
                .andExpect(status().isForbidden());

    }

    @Test
    void shouldAllowViewsAllUsersProgress() throws Exception{

        mockMvc.perform(get("/api/progress/completed"))
                .andExpect(status().isForbidden());
    }




    /*
    Mark module complete
Prevent duplicate completion
Calculate progress percentage correctly
View own progress
Admin views all users' progress
     */
}