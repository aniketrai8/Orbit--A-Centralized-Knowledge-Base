package com.example.OrbitOnboarding.dto.response;


import com.example.OrbitOnboarding.entity.ArticleCategory;
import lombok.Getter;
import lombok.Setter;

//GET /api/knowledge - Returns list without full content
@Getter
@Setter
public class KnowledgeArticleListResponse {

    private Long id;
    private String title;
    private ArticleCategory category;





}
