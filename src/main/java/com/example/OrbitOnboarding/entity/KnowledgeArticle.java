package com.example.OrbitOnboarding.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Table(name="knowledge_articles")
@NoArgsConstructor
@Getter
@Setter
public class KnowledgeArticle {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Size(min=5,max=200)
    @Column(unique = true,nullable = false)
    private String title;

    @Size(min=20,max=1000)
    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ArticleCategory category;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="created_by")

    private User createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();

    }
    @PreUpdate
    public void onUpdate(){
        updatedAt = LocalDateTime.now();
    }







}
