package com.example.OrbitOnboarding.service;

import com.example.OrbitOnboarding.dto.response.MyProgressSummary;
import com.example.OrbitOnboarding.dto.response.ProgressSummaryResponse;
import com.example.OrbitOnboarding.entity.ModuleProgress;
import com.example.OrbitOnboarding.entity.TrainingModule;
import com.example.OrbitOnboarding.entity.User;
import com.example.OrbitOnboarding.repository.ModuleProgressRepository;
import com.example.OrbitOnboarding.repository.TrainingModuleRepository;
import com.example.OrbitOnboarding.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
 class ModuleProgressServiceTest {

@Mock
    private TrainingModuleRepository moduleRepository;

@Mock
    private ModuleProgressRepository progressRepository;

@Mock
    private UserRepository userRepository;

@InjectMocks
    private ModuleProgressService service;

private User user;
 //mocking locked-in User
@BeforeEach
    void setUpSecurityContext(){

    user = new User();
    user.setUsername("testcase");

    var auth= new UsernamePasswordAuthenticationToken(
                    "testcase",null,null
    );

    SecurityContextHolder.getContext().setAuthentication(auth);

    when(userRepository.findByUsername(anyString())) //
            .thenReturn(Optional.of(user));
}

//To Test Percentage accurately
@Test
    void shouldCalculateProgressCorrectly(){
    when(moduleRepository.count()).thenReturn(10L);
    when(progressRepository.countByUserAndCompletedTrue(user)).thenReturn(4L);


    MyProgressSummary response = service.getMyProgress();

    assertEquals(10, response.getSummary().getTotalModules());
    assertEquals(4, response.getSummary().getCompletedModules());
    assertEquals(40.0, response.getSummary().getProgressPercentage());


}

//markModuleAsCompleted

@Test
    void shouldMarkModuleComplete(){

    TrainingModule module= new TrainingModule();

    when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));

    when(progressRepository.findByUserAndModule(user,module)).thenReturn(Optional.empty());


    service.markModuleComplete(1L);
    verify(progressRepository,times(1)).save(any(ModuleProgress.class)); //

}

//duplicate completion
    @Test
    void shouldThrowExceptionIfAlreadyCompleted(){

    TrainingModule module= new TrainingModule();

    when(moduleRepository.findById(1L))
            .thenReturn(Optional.of(module)); //

    when(progressRepository.findByUserAndModule(user,module))
            .thenReturn(Optional.of(new ModuleProgress()));

    assertThrows(RuntimeException.class, ()-> service.markModuleComplete(1L));

    }

    @AfterEach
    void clearContext(){
    SecurityContextHolder.clearContext();
    }

}
