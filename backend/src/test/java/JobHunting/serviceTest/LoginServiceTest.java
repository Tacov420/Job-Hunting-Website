package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import JobHunting.model.*;
import JobHunting.repository.*;
import JobHunting.service.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock 
    private ProfileRepository profileRepository;

    @InjectMocks
    private LoginService loginService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testLogin() {
        Profile profile = new Profile();
        profile.setUserName("test0");
        profile.setPassword(passwordEncoder.encode("test0"));
        profile.setEmail("test0@gmail.com");
        profile.setRegisterStage(2);
        profile.setDesiredJobsLocation(List.of("Taiwan", "Taipei"));
        profile.setDesiredJobsTitle(List.of("Backend Engineer", "Frontend Engineer"));
        profile.setSkills(List.of("sleeping"));
        
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        String result = loginService.login("test0", "test0");
        assertEquals("Login successfully", result);

        String resultPassword = loginService.login("test0", "test");
        assertEquals("Password is incorrect", resultPassword);

        when(profileRepository.findByUserName("test0")).thenReturn(null);
        String resultUserName = loginService.login("test0", "test0");
        assertEquals("Username hasn't been registered", resultUserName);

        profile.setRegisterStage(0);
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        String resultStage0 = loginService.login("test0", "test0");
        assertEquals("Hasn't verified", resultStage0);

        profile.setRegisterStage(1);
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        String resultStage1 = loginService.login("test0", "test0");
        assertEquals("Hasn't filled in preference", resultStage1);
    }
}
