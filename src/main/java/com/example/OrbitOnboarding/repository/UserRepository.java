package com.example.OrbitOnboarding.repository;

import com.example.OrbitOnboarding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {


    /**
     * @param username
     * @return
     */
    //SELECT * FROM users WHERE username=?;
    Optional<User> findByUsername(String username);//required at login

    /**
     * @param email
     * @return
     */
    //SELECT * FROM users WHERE email=? ;
    Optional<User> findByEmail(String email);//required at login

    //think maybe I can fix both one as together

    /**
     * @param username
     * @return
     */
    //SELECT 1 FROM users WHERE username=? ;
    boolean existsByUsername(String username);//to avoid dupilicate enteries

    //SELECT 1 FROM users WHERE email =? ;
    boolean existsByEmail(String email);



}
