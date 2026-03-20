package com.example.OrbitOnboarding.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter

public class TrainingProgressResponse {

    private Long moduleId;
    private String moduleTitle;
    private Boolean completed;
    private LocalDateTime completedAt;




    //check for correct field validation
}
