package com.example.OrbitOnboarding.repository;

import com.example.OrbitOnboarding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {


    //SELECT * FROM users WHERE username=? LIMIT 1;
    Optional<User> findByUsername(String username);//required at login

    //SELECT * FROM users WHERE email=? LIMIT 1;
    Optional<User> findByEmail(String email);//required at login

    //think maybe i can fix both one as together

    //SELECT 1 FROM users WHERE username=? LIMIT 1;
    boolean existsByUsername(String username);//to avoid dupilicate enteries

    //SELECT 1 FROM users WHERE email =? LIMIT 1;
    boolean existsByEmail(String email);



}
