package com.example.OrbitOnboarding.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {


 @NotBlank(message="Username should not be empty")
 @Size(min=3,max=50)
    private String username;
 @NotBlank(message="Password should not be empty")
 @Size(min=8,max=16)
    private String password;



}
