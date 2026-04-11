package com.example.OrbitOnboarding.unit;

import com.example.OrbitOnboarding.dto.request.KnowledgeCreateRequest;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleListResponse;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleResponse;
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

    private final KnowledgeArticleRepository repository;
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

        KnowledgeArticle saved = repository.save(article);

        return mapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    public List<KnowledgeArticleListResponse> listAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toListResponse)
                .toList();
    }


    @Transactional(readOnly = true )
    public KnowledgeArticleResponse get(Long id) {

        KnowledgeArticle article = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        return mapper.toResponse(article);
    }


    @Transactional(readOnly = true)
    public List<KnowledgeArticleListResponse> search(String keyword) {

        return repository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                        keyword, keyword)
                .stream()
                .map(mapper::toListResponse)
                .toList();
    }

    //add update method
    @Transactional
    public KnowledgeArticleResponse update(Long id,
                                           KnowledgeCreateRequest request) {

        KnowledgeArticle article = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setCategory(request.getCategory());

        KnowledgeArticle saved = repository.save(article);

        return mapper.toResponse(saved);
    }

    //add delete method
    @Transactional
    public void delete(Long id) {

        KnowledgeArticle article = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article does not exist"));

        repository.delete(article);
    }
}