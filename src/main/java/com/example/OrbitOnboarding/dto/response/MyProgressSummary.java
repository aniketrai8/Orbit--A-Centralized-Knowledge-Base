package com.example.OrbitOnboarding.dto.response;


import com.example.OrbitOnboarding.entity.TrainingModule;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyProgressSummary {

    private UserInfo user;
    private ProgressSummaryResponse summary;
    private List<TrainingProgressResponse> moduleDetails;

    //passed in a List

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo{
        private String username;
        private String fullName;

    }
}
