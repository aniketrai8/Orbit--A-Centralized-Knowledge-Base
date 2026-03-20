package com.example.OrbitOnboarding.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class ProgressSummaryResponse {

    private int totalModules;
    private int completedModules;
    private double progressPercentage;


}
