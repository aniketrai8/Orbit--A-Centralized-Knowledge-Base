package com.example.OrbitOnboarding.unit;


import com.example.OrbitOnboarding.dto.request.LoginRequest;
import com.example.OrbitOnboarding.dto.request.RegisterRequest;
import com.example.OrbitOnboarding.dto.response.AuthResponse;
import com.example.OrbitOnboarding.dto.response.RegisterResponse;
import com.example.OrbitOnboarding.entity.Role;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.exception.BadRequestException;
import com.example.OrbitOnboarding.repository.UserRepository;
import com.example.OrbitOnboarding.service.AuthService;
import com.example.OrbitOnboarding.service.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith({org.mockito.junit.jupiter.MockitoExtension.class})
 class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    //Mocking a user registration

    /**
     * @return
     */
    private RegisterRequest registerRequest() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("aniket");
        request.setEmail("aniket@molx.com");
        request.setPassword("Aniket101");
        request.setFullName("Aniket Rai");
        return request;
    }
    /**
     * @return
     */
    private LoginRequest loginRequest() {

        LoginRequest request = new LoginRequest();
        request.setUsername("aniket");
        request.setPassword("Aniket101");
        return request;
    }

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request = registerRequest();

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("aniket");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        RegisterResponse result = authService.register(request);

        assertEquals("User Registered Successfully", result.getMessage());
        assertEquals("aniket", result.getUsername());
        assertEquals(1L, result.getUserId());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowUsernameIfExist(){

        RegisterRequest request = registerRequest();
        when(userRepository.existsByUsername("aniket"))
                .thenReturn(true);
        assertThrows(BadRequestException.class,
                () -> authService.register(request)); //
    }

    //Tests logging in of a User
    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = loginRequest();
        User user = new User();
        user.setUsername("aniket");
        user.setPassword("encoded");
        user.setRole(Role.USER);
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString()))
                .thenReturn("jwt-token");
        AuthResponse response = authService.login(request);
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void shouldThrowIfPasswordInvalid() {

        LoginRequest request = new LoginRequest();
        User user = new User();
        user.setUsername("aniket");
        user.setPassword("Aniket101");
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(false);
        assertThrows(RuntimeException.class,
                () -> authService.login(request));
    }
}
