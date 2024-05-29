package JobHunting;

// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import java.util.List;

import JobHunting.repository.*;
import JobHunting.model.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private ProfileRepository profileRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void setUp() {
        Profile profile = new Profile();
        profile.setUserName("test0");
        profile.setPassword(passwordEncoder.encode("test0"));
        profile.setEmail("test0@gmail.com");
        profile.setRegisterStage(2);
        profile.setDesiredJobsLocation(List.of("Taiwan", "Taipei"));
        profile.setDesiredJobsTitle(List.of("Backend Engineer", "Frontend Engineer"));
        profile.setSkills(List.of("sleeping"));
        profileRepository.save(profile);
    }

    public void setUpForRegisterStage1() {
        Profile profile = new Profile();
        profile.setUserName("test0");
        profile.setPassword(passwordEncoder.encode("test0"));
        profile.setEmail("test0@gmail.com");
        profile.setRegisterStage(1);
        profileRepository.save(profile);
    }

    public void setUpForRegisterStage0() {
        Profile profile = new Profile();
        profile.setUserName("test0");
        profile.setPassword(passwordEncoder.encode("test0"));
        profile.setEmail("test0@gmail.com");
        profile.setRegisterStage(0);
        profileRepository.save(profile);
    }

    @AfterEach
    public void tearDown() {
        profileRepository.deleteAll();
    }
    

    // =============== /api/login ===============
    // --------------- For login successfully ---------------
    @Test
    public void testLogin() throws Exception {
        setUp();

        String requestBody = "{\"userName\": \"test0\", \"password\": \"test0\"}";
        
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().string("Login successfully"));
    }

    // --------------- For incorrect password ---------------
    @Test
    public void testLoginIncorrectPassword() throws Exception {
        setUp();
        
        String requestBody = "{\"userName\": \"test0\", \"password\": \"tests\"}";
        
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Password is incorrect"));
    }

    // --------------- For username doesn't exist ---------------
    @Test
    public void testLoginNoUserName() throws Exception {
        setUp();

        String requestBody = "{\"userName\": \"777\", \"password\": \"tests\"}";
        
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Username hasn't been registered"));
    }

    // --------------- For registerStage0 ---------------
    @Test
    public void testRegisterStage0() throws Exception {
        setUpForRegisterStage0();
        
        String requestBody = "{\"userName\": \"test0\", \"password\": \"test0\"}";
        
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Hasn't verified"));
    }

    // --------------- For registerStage1 ---------------
    @Test
    public void testRegisterStage1() throws Exception {
        setUpForRegisterStage1();

        String requestBody = "{\"userName\": \"test0\", \"password\": \"test0\"}";
        
        mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Hasn't filled in preference"));
    }
}
