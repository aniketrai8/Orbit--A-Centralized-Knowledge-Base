package com.example.OrbitOnboarding.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.lang.model.element.ModuleElement;
import java.time.LocalDateTime;

/*id: Primary key (auto-generated)
userId: Foreign key to User
trainingModuleId: Foreign key to TrainingModule
completed: Boolean (default: false)
completedAt: Timestamp (nullable)
createdAt: Timestamp

 */

@Entity
@Table(name="module_progress",uniqueConstraints={@UniqueConstraint(columnNames={"user_id","trainingModule_id"})
   }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable=false)
    @NotNull
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="trainingModule_id",nullable=false)
    private TrainingModule module;

    private boolean completed = false;  //default value False

    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

@Builder
    public ModuleProgress(User user,TrainingModule module,boolean completed,LocalDateTime createdAt,LocalDateTime completedAt){
    this.user=user;
    this.module=module;
    this.completed=false;
    this.createdAt=LocalDateTime.now();
    this.completedAt=LocalDateTime.now();
}





//mention isCompleted in GitHub
    //Builder and noArgsConstructor cannot be used together


}
