package com.example.OrbitOnboarding.dto.request;

import jakarta.validation.constraints.NotBlank;
import com.example.OrbitOnboarding.entity.ArticleCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeCreateRequest {

    private ArticleCategory category;

    @NotBlank
    private String title;

    @NotBlank
    private String content;




}
