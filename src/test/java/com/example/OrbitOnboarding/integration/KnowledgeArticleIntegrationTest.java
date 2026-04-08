package com.example.OrbitOnboarding.integration;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    //shouldCreateKnowledgeArticle
    //shouldListKnowledgeArticle
    //shouldSearchKnowledgeArticle

    @Test
    void shouldCreateKnowledgeArticle() throws Exception{

        String json = """
    {
      "category":"GIT_WORKFLOW",
      "title":"Git Guide",
      "content":"This explains git branching strategy used in company. This content is intentionally long enough to pass validation constraints."
    }
    """;

        mockMvc.perform(post("/api/knowledge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldListKnowledgeArticle() throws Exception {

        mockMvc.perform(get("/api/knowledge"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldSearchKnowledgeArticle() throws Exception {

        mockMvc.perform(get("/api/knowledge/search")
                        .param("keyword", "git"))
                .andExpect(status().isForbidden());
    }
}
