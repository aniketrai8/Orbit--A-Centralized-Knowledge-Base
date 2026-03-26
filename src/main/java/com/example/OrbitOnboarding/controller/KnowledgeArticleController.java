package com.example.OrbitOnboarding.controller;

import com.example.OrbitOnboarding.dto.request.KnowledgeCreateRequest;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleListResponse;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleResponse;
import com.example.OrbitOnboarding.service.KnowledgeArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Tag(name="Knowledge Article API",description = "Handles CRUD operations and search features")
@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
public class KnowledgeArticleController {

    private final KnowledgeArticleService service;


    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<KnowledgeArticleListResponse> listAll() {
        return service.listAll();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public KnowledgeArticleResponse get(@PathVariable Long id) {
        return service.get(id);
    }


    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<KnowledgeArticleListResponse> search(
            @RequestParam String keyword) {
        return service.search(keyword);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public KnowledgeArticleResponse create(
            @RequestBody KnowledgeCreateRequest request) {
        return service.create(request);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public KnowledgeArticleResponse update(
            @PathVariable Long id,
            @RequestBody KnowledgeCreateRequest request) {
        return service.update(id, request);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
         return ResponseEntity.ok("Article Deletion was a Success");
    }
}