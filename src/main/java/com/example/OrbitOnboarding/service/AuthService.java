package com.example.OrbitOnboarding.service;


import com.example.OrbitOnboarding.dto.request.LoginRequest;
import com.example.OrbitOnboarding.dto.request.RegisterRequest;
import com.example.OrbitOnboarding.dto.response.AuthResponse;
import com.example.OrbitOnboarding.dto.response.RegisterResponse;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.exception.BadRequestException;
import com.example.OrbitOnboarding.exception.GlobalExceptionHandler;
import com.example.OrbitOnboarding.exception.ResourceNotFoundException;
import com.example.OrbitOnboarding.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.OrbitOnboarding.entity.Role;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;



@Getter
@AllArgsConstructor
@Service
public class AuthService {

    private static final Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    /**
     * @param request Controller migrates to service layer where it checks
     *                - if username exists or not
     *                - if the email exists or not
     *                - Creates a new user with all required fields
     *                - Saves it under repository
     * @return
     */
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        log.info("Register attempt for username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("Registration failed - Username already exists: {}", request.getUsername());
            throw new BadRequestException("Username already exists");
        }


        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("Registration failed - Email already exists: {}",request.getEmail());
            throw new BadRequestException("Email exists");
        }


        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        log.info("User registered successfuly with id: {}",savedUser.getId());

        return RegisterResponse.builder()
                .message("User Registered Successfully")
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .build();

    }


    /**
     * @param request Controller navigates to service layer where it checks
     *                - if the username and password exists within the system
     *                - JWT generate Token
     *                - Builds and returns DTO
     * @return
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {

        log.info("Login attempt for username: {}",request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.error("Login failed - User not found: {}", request.getUsername());
                    return new BadRequestException("User not found");

                });
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Login failed - Invalid password for user: {}", request.getUsername());
            throw new BadRequestException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
}