package com.example.OrbitOnboarding.integration;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 */
@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testcontainers")
@Import(org.testcontainers.utility.TestcontainersConfiguration.class)
public abstract class BaseIntegrationTest extends ContainerPropertyInitializer{

    @Autowired
    protected MockMvc mockMvc;

}
