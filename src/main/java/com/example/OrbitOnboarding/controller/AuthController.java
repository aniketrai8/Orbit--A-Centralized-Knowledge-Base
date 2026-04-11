package com.example.OrbitOnboarding.controller;

import com.example.OrbitOnboarding.dto.request.LoginRequest;
import com.example.OrbitOnboarding.dto.request.RegisterRequest;
import com.example.OrbitOnboarding.dto.response.AuthResponse;
import com.example.OrbitOnboarding.unit.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name="Register and Login APIs ",description = "Handles Registration and Login of Users and ADMIN")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    /**
     * @param request Makes use of @RequestBody to read through user credentials and then is sent over to the desired service layer method
     * @return
     */
    @PostMapping("/register")
    @Operation(summary="Registers a new user", description = "User credentials should not be reused")
    public String register(@Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }


    /**
     * @param request Makes use of @RequestBody to read through user credentials and then is sent over to the desired service layer method
     * @return
     */
    @PostMapping("/login")
    @Operation(summary="Logins an existing user", description = "Recheck for valid credentials")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}