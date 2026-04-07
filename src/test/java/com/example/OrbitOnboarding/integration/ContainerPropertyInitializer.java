package com.example.OrbitOnboarding.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class ContainerPropertyInitializer {

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url",
                () -> TestcontainersConfiguration.postgres.getJdbcUrl());

        registry.add("spring.datasource.username",
                () -> TestcontainersConfiguration.postgres.getUsername());

        registry.add("spring.datasource.password",
                () -> TestcontainersConfiguration.postgres.getPassword());

        registry.add("spring.jpa.hibernate.ddl-auto",
                () -> "create-drop");
    }
}
