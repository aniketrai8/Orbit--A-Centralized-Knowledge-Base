package com.example.OrbitOnboarding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrainingModuleDelete {

    private Long id;
    private String title;
    private String description;
    private String message;
}
