package com.example.OrbitOnboarding.integration;

import com.example.OrbitOnboarding.config.PostgresContainerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testcontainers")

public abstract class BaseIntegrationTest extends PostgresContainerBase {

    @Autowired
    protected MockMvc mockMvc;

}
