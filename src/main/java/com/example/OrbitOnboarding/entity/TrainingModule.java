package com.example.OrbitOnboarding.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="training_module")
public class TrainingModule {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=5, max=200)
    @Column(unique=true,nullable=false)
    private String title;

    @NotNull
    @Size(min=10,max=500)
    @Column(nullable=false)
    private String description;

    @NotNull
    @Size(min=50)
    @Column(columnDefinition="Text",nullable= false)
    private String content;

    @NotNull
    @Column(nullable=false,unique = true)
    private Integer moduleOrder;

    @NotNull
    @Positive
    @Max(40)
    private Integer estimatedHour;

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


