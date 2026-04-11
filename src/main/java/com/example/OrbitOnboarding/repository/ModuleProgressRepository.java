package com.example.OrbitOnboarding.repository;

import com.example.OrbitOnboarding.entity.ModuleProgress;
import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleProgressRepository extends JpaRepository<ModuleProgress,Long> {



    Optional<ModuleProgress> findByUserAndModule(User user, TrainingModule module);

    //SELECT * FROM module_progress mp WHERE mp.user_id=?;
    List<ModuleProgress> findByUser(User user);

    //SELECT COUNT(*)
    long countByUser(User user);

    /*SELECT COUNT(*)
    FROM module_progress mp
    WHERE mp.user_id = 1
    AND completed = true;
     */
    long countByUserAndCompletedTrue(User user);





}
