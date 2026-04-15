package com.example.OrbitOnboarding.service;

import com.example.OrbitOnboarding.dto.request.KnowledgeCreateRequest;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleDeleteResponse;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleListResponse;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleResponse;
import com.example.OrbitOnboarding.dto.response.RegisterResponse;
import com.example.OrbitOnboarding.entity.ArticleCategory;
import com.example.OrbitOnboarding.entity.KnowledgeArticle;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.exception.ResourceNotFoundException;
import com.example.OrbitOnboarding.mapper.KnowledgeArticleMapper;
import com.example.OrbitOnboarding.repository.KnowledgeArticleRepository;
import com.example.OrbitOnboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeArticleService {

    private final KnowledgeArticleRepository knowledgeRepository;
    private final KnowledgeArticleMapper mapper;
    private final UserRepository userRepository;
    private ArticleCategory category;


    @Transactional
    public KnowledgeArticleResponse create(KnowledgeCreateRequest request) {

        KnowledgeArticle article = mapper.toEntity(request);
        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();
        User creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        article.setCreatedBy(creator);
        KnowledgeArticle saved = knowledgeRepository.save(article);
        return mapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    public List<KnowledgeArticleListResponse> listAll() {

        return knowledgeRepository.findAll()
                .stream()
                .map(mapper::toListResponse)
                .toList();
    }


    @Transactional(readOnly = true )
    public KnowledgeArticleResponse get(Long id) {

        KnowledgeArticle article = knowledgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        return mapper.toResponse(article);
    }


    @Transactional(readOnly = true)
    public List<KnowledgeArticleListResponse> search(String keyword) {

        return knowledgeRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                        keyword, keyword)
                .stream()
                .map(mapper::toListResponse)
                .toList();
    }

    //add update method
    @Transactional
    public KnowledgeArticleResponse update(Long id, KnowledgeCreateRequest request) {

        KnowledgeArticle article = knowledgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setCategory(request.getCategory());
        KnowledgeArticle saved = knowledgeRepository.save(article);
        return mapper.toResponse(saved);
    }

    //add delete method
    @Transactional
    public KnowledgeArticleDeleteResponse delete(Long id) {

        KnowledgeArticle article = knowledgeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article does not exist"));

        KnowledgeArticleDeleteResponse response = new KnowledgeArticleDeleteResponse(
                article.getId(),
                article.getCategory(),
                article.getTitle(),
                "Article Deleted Successfully"
                );
        knowledgeRepository.delete(article);
        return response;
    }
}