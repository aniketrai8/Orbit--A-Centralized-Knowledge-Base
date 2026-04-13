package com.example.OrbitOnboarding.controller;

import com.example.OrbitOnboarding.dto.request.KnowledgeCreateRequest;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleListResponse;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleResponse;
import com.example.OrbitOnboarding.service.KnowledgeArticleService;
import io.swagger.v3.oas.annotations.Operation;
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

    /**
     * @return Controller layer redirects to the service layer method responsible to fetch all articles.
     */
    @GetMapping
    @Operation(summary = "Lists down all existing articles",description =" ")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<KnowledgeArticleListResponse>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    /**
     * @param id Uses @Path Variable Annotation to extract values from the path of a request and helps to fetch the requested document
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "Lists only the desired Article",description = "The Requested article should exist")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<KnowledgeArticleResponse>get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    /**
     * @param keyword Uses @RequestParam to extract the query parameters to enable searching a desired word through the document
     * @return
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Returns Articles that matches the suggested keyword",description = "")
    public ResponseEntity<List<KnowledgeArticleListResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.search(keyword));
    }

    /**
     * @param request Uses @Request Body,to read through the request body and passed it through the service method
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Creates a new Knowledge Article",description = "Only for ADMIN")
    public ResponseEntity<KnowledgeArticleResponse> create(@RequestBody KnowledgeCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    /**
     * @param id
     * @param request Identifies the requested article and then passes it through with @RequestBody to service method
     * @return
     */
    @PutMapping("/{id}")
    @Operation(summary = "Responsible for Updating an Article content",description = "Only for ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KnowledgeArticleResponse> update(@PathVariable Long id, @RequestBody KnowledgeCreateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    /**
     * @param id  Uses @PathVariable to identify the requested article and then passes it down to service layer
     * @return
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an existing article",description = "Only for ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
         return ResponseEntity.ok("Article Deletion was a Success");
    }
}