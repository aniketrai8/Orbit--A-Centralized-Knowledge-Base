

package com.example.OrbitOnboarding.dto.response;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class TrainingModuleResponse {



    private Long id;
    private String title;
    private String description;
    private String content;
    private Integer moduleOrder;
    private Integer estimatedHour;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}


