package com.example.OrbitOnboarding.unit;


import com.example.OrbitOnboarding.dto.request.KnowledgeCreateRequest;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleResponse;
import com.example.OrbitOnboarding.entity.KnowledgeArticle;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.mapper.KnowledgeArticleMapper;
import com.example.OrbitOnboarding.repository.KnowledgeArticleRepository;
import com.example.OrbitOnboarding.repository.UserRepository;
import com.example.OrbitOnboarding.service.KnowledgeArticleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class KnowledgeArticleServiceTest {

    @Mock
    private KnowledgeArticleRepository repository;

    @Mock
    private KnowledgeArticleMapper mapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private KnowledgeArticleService service;

    private User user;

    @BeforeEach
    void setupSecurity(){

        user = new User();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("aniket", null)
        );

        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(user));
    }

    @AfterEach
    void clear() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldCreateArticle() {

        KnowledgeCreateRequest request = new KnowledgeCreateRequest();
        KnowledgeArticle entity = new KnowledgeArticle();
        KnowledgeArticleResponse response = new KnowledgeArticleResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        KnowledgeArticleResponse result = service.create(request);

        assertEquals(response, result);
        verify(repository).save(entity);
    }



}
