package com.example.OrbitOnboarding.entity;

/*id: Primary key (auto-generated)
title: 5-200 characters, required
description: 10-500 characters, required
content: Minimum 50 characters, required (TEXT type)
moduleOrder: Positive integer, unique (sequence: 1, 2, 3...) //unique
estimatedHours: 1-40 hours, required
createdBy: Foreign key to User
createdAt: Timestamp
updatedAt: Timestamp



 */

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
    @Column(nullable=false)
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
    @Column(nullable=false)
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


