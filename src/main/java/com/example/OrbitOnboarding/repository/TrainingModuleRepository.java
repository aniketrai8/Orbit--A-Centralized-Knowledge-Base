
package com.example.OrbitOnboarding.repository;

import com.example.OrbitOnboarding.entity.TrainingModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingModuleRepository extends JpaRepository<TrainingModule,Long> {

    //SELECT * FROM training_module ORDER BY ASC;
    List<TrainingModule> findAllByOrderByModuleOrderAsc();
}


