package com.example.OrbitOnboarding.integration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(
                    DockerImageName.parse("postgres:15-alpine"))
                    .withDatabaseName("orbit_test")
                    .withUsername("test")
                    .withPassword("test");

    static {
        postgres.start();
    }

    @Bean
    PostgreSQLContainer<?> postgresContainer() {
        return postgres;
    }
}
