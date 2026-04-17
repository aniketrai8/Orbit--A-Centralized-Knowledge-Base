package com.example.OrbitOnboarding.integration;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aims to provide a common annotation for all IT tests
 */
@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@ActiveProfiles("testcontainers")
@Import(org.testcontainers.utility.TestcontainersConfiguration.class)
public abstract class BaseIntegrationTest extends ContainerPropertyInitializer{

    @Autowired
    protected MockMvc mockMvc;

}
