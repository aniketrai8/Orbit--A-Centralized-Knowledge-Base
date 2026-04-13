package com.example.OrbitOnboarding.dto.response;

import com.example.OrbitOnboarding.entity.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private String message;
    private Long userId;
    private String username;

}