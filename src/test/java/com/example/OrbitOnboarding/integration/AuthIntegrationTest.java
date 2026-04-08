package com.example.OrbitOnboarding.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

public class AuthIntegrationTest extends BaseIntegrationTest {


    private String uniqueUsername() {
        return "integration_user_" +
                UUID.randomUUID().toString().substring(0, 8);
    }

    private String buildRegisterRequest(String username) {
        return """
            {
              "username":"%s",
              "email":"%s@molex.com",
              "password":"password123",
              "fullName":"Integration User"
            }
            """.formatted(username, username);
    }

    private String buildLoginRequest(String username) {
        return """
            {
              "username":"%s",
              "password":"password123"
            }
            """.formatted(username);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {

        String username = uniqueUsername();


        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRegisterRequest(username)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldLoginAndReturnJwt() throws Exception {

        String username = uniqueUsername();


        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildRegisterRequest(username)))
                .andExpect(status().isOk());


        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildLoginRequest(username)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void shouldRejectInvalidCredentials() throws Exception{
        String username = uniqueUsername();

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildRegisterRequest(username)))
                .andExpect(status().isOk());

        String invalidLogin = """
            {
              "username":"%s",
              "password":"wrongpassword"
            }
            """.formatted(username);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidLogin))
                .andExpect(status().isUnauthorized());

        }

        @Test
    void shouldANotAllowDuplicateUsername() throws Exception{

        String username = uniqueUsername();

            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                            .content(buildRegisterRequest(username)))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(buildRegisterRequest(username)))
                    .andExpect(status().is4xxClientError());

    }

    @Test
    void shouldRejectAccessWithoutToken() throws Exception{

        mockMvc.perform(get("/api/training-modules"))
                .andExpect(status().isUnauthorized());
    }

    //shouldRejectInvalidCredentials
    //shouldNotAllowDuplicateUsername
    //shouldRejectAccessWithoutToken

}