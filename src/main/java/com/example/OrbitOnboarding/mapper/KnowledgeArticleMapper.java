package com.example.OrbitOnboarding.mapper;

import com.example.OrbitOnboarding.dto.request.KnowledgeCreateRequest;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleListResponse;
import com.example.OrbitOnboarding.dto.response.KnowledgeArticleResponse;
import com.example.OrbitOnboarding.entity.KnowledgeArticle;
import com.example.OrbitOnboarding.mapper.KnowledgeArticleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel= "spring") //refresh dependencies
public interface KnowledgeArticleMapper{

    KnowledgeArticle toEntity(KnowledgeCreateRequest request);

    @Mapping(source= "createdBy.username", target="createdBy") //
    KnowledgeArticleResponse toResponse(KnowledgeArticle article);

    KnowledgeArticleListResponse toListResponse(KnowledgeArticle article);
}