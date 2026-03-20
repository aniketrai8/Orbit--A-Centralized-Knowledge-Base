package com.example.OrbitOnboarding.repository;

import com.example.OrbitOnboarding.entity.KnowledgeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeArticleRepository extends JpaRepository<KnowledgeArticle,Long> {

    //search feature
    //not case-sensitive

    List<KnowledgeArticle> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title,
            String content
    );

}
