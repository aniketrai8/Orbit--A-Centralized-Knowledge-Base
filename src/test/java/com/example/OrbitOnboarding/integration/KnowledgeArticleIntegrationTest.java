package com.example.OrbitOnboarding.integration;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

public class KnowledgeArticleIntegrationTest extends BaseIntegrationTest{

    @Test
    void shouldRejectUnauthorizedArticleCreation() throws Exception {

        String json = """
        {
          "category":"GIT_WORKFLOW",
          "title":"Git Guide",
          "content":"This explains git branching strategy used in company.This is a detailed training module content used only for " +
                                                                                      "integration testing purposes and must exceed fifty characters."
        }
        """;

        mockMvc.perform(post("/api/knowledge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }
}
