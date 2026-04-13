package com.example.OrbitOnboarding.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message="Username cannot be blank")
    private String username;

    @NotBlank
    @Size(min=8)
    private String password;

    @NotBlank(message="Email cannot be blank")
    @Pattern(regexp=".*@molex\\.com$")
    private String email;

    @NotBlank(message="Please enter full name")
    private String fullName;

}
