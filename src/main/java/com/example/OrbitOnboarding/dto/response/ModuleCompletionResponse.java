package com.example.OrbitOnboarding.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleCompletionResponse {

    private String message;
    private Long moduleId;
    private String moduleTitle;
    private LocalDateTime completedAt;
    private double progressPercentage;

}
