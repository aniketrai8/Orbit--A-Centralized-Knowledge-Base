package com.example.OrbitOnboarding.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min=8)
    private String password;

    @NotBlank
    @Pattern(regexp=".*@molex\\.com$")
    private String email;

    @NotBlank
    private String fullName;

    //why things like role is not asked even though at registration point all information should be collected



}
