package com.example.OrbitOnboarding.dto.response;

import com.example.OrbitOnboarding.entity.ArticleCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeArticleResponse {

    private ArticleCategory category;

    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private String createdAt;


}
