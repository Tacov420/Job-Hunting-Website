package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import JobHunting.model.*;
import JobHunting.repository.*;
import JobHunting.service.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {
    @Mock 
    private ProfileRepository profileRepository;

    @InjectMocks
    private RegisterService registerService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testRegister() {        
        when(profileRepository.findByUserName("test0")).thenReturn(null);
        String resultFirst = registerService.registerPersonalInfo("test0", "test0");
        assertEquals("Personal info registered successfully", resultFirst);

        Profile profile = new Profile();
        profile.setUserName("test0");
        profile.setPassword(passwordEncoder.encode("test0"));

        when(profileRepository.findFirstByOrderByIdDesc()).thenReturn(profile);
        String resultSecond = registerService.registerPersonalInfo("test0", "test0");
        assertEquals("Personal info registered successfully", resultSecond);
    }

    @Test
    public void testSendVerificationCode() {        
        when(profileRepository.findByUserName("test0")).thenReturn(null);
        String resultUserName = registerService.registerSendVerificationCode("test0", "test0@gmail.com");
        assertEquals("Username doesn't exist", resultUserName);

        Profile profile0 = new Profile();
        profile0.setUserName("test0");
        profile0.setPassword(passwordEncoder.encode("test0"));
        profile0.setEmail("test0@gmail.com");

        Profile profile1 = new Profile();
        profile1.setUserName("test1");
        profile1.setPassword(passwordEncoder.encode("test1"));

        when(profileRepository.findByEmail("test0@gmail.com")).thenReturn(profile0);
        when(profileRepository.findByUserName("test1")).thenReturn(profile1);
        String resultEmail = registerService.registerSendVerificationCode("test1", "test0@gmail.com");
        assertEquals("Email address has already been registered", resultEmail);

        String result = registerService.registerSendVerificationCode("test1", "test1@gmail.com");
        assertEquals("Send Email successfully", result);
    }

    @Test
    public void testVerifyCode() {        
        when(profileRepository.findByUserName("test0")).thenReturn(null);
        String resultUserName = registerService.verifyCode("test0", "qwerdf");
        assertEquals("Username doesn't exist", resultUserName);

        Profile profile0 = new Profile();
        profile0.setUserName("test0");
        profile0.setPassword(passwordEncoder.encode("test0"));

        when(profileRepository.findByUserName("test0")).thenReturn(profile0);
        String resultEmail = registerService.verifyCode("test0", "qwerdf");
        assertEquals("Email hasn't been registered", resultEmail);
        profile0.setVerificationCode("qwerdf");

        String resultVerificationCode = registerService.verifyCode("test0", "fdrewq");
        assertEquals("Verification Code is incorrect", resultVerificationCode);

        String result = registerService.verifyCode("test0", "qwerdf");
        assertEquals("Verify successfully", result);
    }

    @Test
    public void testRegisterPreference() {
        List<String> jobsTitle = new ArrayList<>(Arrays.asList("Backend Engineer", "Frontend Engineer"));
        List<String> jobsLocation = new ArrayList<>(Arrays.asList("Taiwan", "America"));
        List<String> skills = new ArrayList<>(Arrays.asList("sleep"));
        List<String> companies = new ArrayList<>(Arrays.asList("Google", "Microsoft"));

        when(profileRepository.findByUserName("test0")).thenReturn(null);
        String resultUserName = registerService.registerPreference("test0", jobsTitle, jobsLocation, skills, companies);
        assertEquals("Username doesn't exist", resultUserName);

        Profile profile = new Profile();
        profile.setUserName("test0");
        profile.setPassword(passwordEncoder.encode("test0"));
        profile.setEmail("test0@gmail.com");
        profile.setRegisterStage(0);
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        String resultStage0 = registerService.registerPreference("test0", jobsTitle, jobsLocation, skills, companies);
        assertEquals("Stage incorrect", resultStage0);

        profile.setRegisterStage(2);
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        String resultStage2 = registerService.registerPreference("test0", jobsTitle, jobsLocation, skills, companies);
        assertEquals("Stage incorrect", resultStage2);

        profile.setRegisterStage(1);
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        String result = registerService.registerPreference("test0", jobsTitle, jobsLocation, skills, companies);
        assertEquals("Preference saved successfully", result);
    }
}
