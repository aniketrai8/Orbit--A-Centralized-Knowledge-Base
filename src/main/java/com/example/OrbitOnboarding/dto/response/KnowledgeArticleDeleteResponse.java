package com.example.OrbitOnboarding.dto.response;

import com.example.OrbitOnboarding.entity.ArticleCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class KnowledgeArticleDeleteResponse {

    private Long id;
    private ArticleCategory category;
    private String title;
    private String message;
}
