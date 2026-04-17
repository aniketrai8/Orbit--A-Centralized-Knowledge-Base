package com.example.OrbitOnboarding.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=3,max=50)
    @Column(unique=true,nullable=false)
    private String username;//3-50 characters

    @Email
    @Pattern(
       regexp=".*@molex\\.com$"
    )
    @Column(unique=true,nullable=false)//@molex.com
    private String email;//Unique email password

    @NotBlank
    @Column(nullable=false)
    private String  password;//Bcrypted //incommpatible tyoes while using passwordEncoder

    @Size(min=2,max=100)
    private String fullName; //2-100 characters

    @OneToMany(mappedBy = "createdBy")
    private List<KnowledgeArticle> articles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable= false, updatable=false)
    private LocalDateTime createdAt;

}
