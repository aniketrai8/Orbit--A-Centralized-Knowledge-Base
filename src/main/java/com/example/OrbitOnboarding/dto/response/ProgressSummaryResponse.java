package com.example.OrbitOnboarding.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class ProgressSummaryResponse {

    private long totalModules;
    private long completedModules;
    private double progressPercentage;


}
