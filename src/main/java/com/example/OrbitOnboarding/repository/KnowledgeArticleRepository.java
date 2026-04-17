package com.example.OrbitOnboarding.repository;

import com.example.OrbitOnboarding.entity.KnowledgeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeArticleRepository extends JpaRepository<KnowledgeArticle,Long> {

    /**
     * @param title
     * @param content
     * @return
     */

/*SELECT * FROM knowledge_article WHERE LOWER(title) LIKE LOWER(CONCAT('%spring%')) OR LOWER(content)
LIKE LOWER(CONCAT('%spring%'));

 */

    /*
      SELECT k
      FROM KnowledgeArticle k
    WHERE LOWER(k.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR LOWER(k.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
     */

    List<KnowledgeArticle> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title,
            String content
    );

    boolean existsByTitle(String Title);

}
