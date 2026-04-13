
package com.example.OrbitOnboarding.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TrainingModuleRequest {

    @NotNull
    @Size(min=5,max=200)
    private String title;

    @NotNull
    @Size(min=10,max=500)
    private String description;

    @NotNull
    @Size(min=50)
    private String content;

    @NotNull
    private Integer moduleOrder;

    @NotNull
    private Integer estimatedHour;

}


