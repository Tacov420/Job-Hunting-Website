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
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import JobHunting.repository.*;
import JobHunting.model.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RegisterTest {
    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    public void setUpPersonalInfo(String userName, String password) {
        Profile profile = new Profile();
        profile.setUserName(userName);
        profile.setPassword(passwordEncoder.encode(password));
        profileRepository.save(profile);
    }

    public void setUpSendVerification(String userName, String password, String email, String verificationCode) {
        Profile profile = new Profile();
        profile.setUserName(userName);
        profile.setPassword(passwordEncoder.encode(password));
        profile.setEmail(email);
        profile.setVerificationCode(verificationCode);
        profileRepository.save(profile);
    }

    public void setUpVerify(String userName, String email, String verificationCode) {
        Profile profile = new Profile();
        profile.setUserName(userName);
        profile.setEmail(email);
        profile.setVerificationCode(verificationCode);
        profileRepository.save(profile);
    }

    public void setUpInfo(String userName, String email, int registerStage) {
        Profile profile = new Profile();
        profile.setUserName(userName);
        profile.setEmail(email);
        profile.setRegisterStage(registerStage);
        profileRepository.save(profile);
    }

    @AfterEach
    public void tearDown() {
        profileRepository.deleteAll();
        companyRepository.deleteAll();
    }
    

    // =============== /api/register/personalInfo ===============
    // --------------- For register successfully ---------------
    @Test
    public void testRegister() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        requestBody.put("password", "test0");
        
        mockMvc.perform(post("/api/register/personalInfo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(content().string("Personal info registered successfully")
        );

        Profile profile = profileRepository.findByUserName("test0");
        assertNotNull(profile);
        assertEquals("test0", profile.getUserName());
        assertTrue(passwordEncoder.matches("test0", profile.getPassword()));
        assertEquals(0, profile.getRegisterStage());
    }

    // --------------- For register with repeat userName ---------------
    @Test
    public void testRegisterRepeatUserName() throws Exception {
        setUpPersonalInfo("test0", "test0");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        requestBody.put("password", "7777");
        
        mockMvc.perform(post("/api/register/personalInfo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Username has already been registered")
        );
    }

    // =============== /api/register/sendVerification ===============
    // --------------- For send verification successfully ---------------
    @Test
    public void testSendVerification() throws Exception {
        setUpPersonalInfo("test0", "test0");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        requestBody.put("email", "test0@gmail.com");
        
        mockMvc.perform(post("/api/register/sendVerification")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(content().string("Send Email successfully")
        );

        Profile profile = profileRepository.findByUserName("test0");
        assertNotNull(profile);
        assertEquals("test0", profile.getUserName());
        assertEquals("test0@gmail.com", profile.getEmail());
        assertEquals(0, profile.getRegisterStage());
    }

    // --------------- For username doesn't exist ---------------
    @Test
    public void testSendVerificationUserName() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test1");
        requestBody.put("email", "test1@gmail.com");
        
        mockMvc.perform(post("/api/register/sendVerification")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Username doesn't exist")
        );
    }

    // --------------- For email address has already been registered ---------------
    @Test
    public void testSendVerificationEmail() throws Exception {
        setUpSendVerification("test0", "test0", "test0@gmail.com", "ABCD09");
        setUpPersonalInfo("test1", "test1");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test1");
        requestBody.put("email", "test0@gmail.com");
        
        mockMvc.perform(post("/api/register/sendVerification")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Email address has already been registered")
        );
    }

    // =============== /api/register/verify ===============
    // --------------- For verify successfully ---------------
    @Test
    public void tesVerify() throws Exception {
        setUpSendVerification("test0", "test0", "test0@gmail.com", "ABCD09");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        requestBody.put("verificationCode", "ABCD09");
        
        mockMvc.perform(post("/api/register/verify")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(content().string("Verify successfully")
        );

        Profile profile = profileRepository.findByUserName("test0");
        assertNotNull(profile);
        assertEquals("test0", profile.getUserName());
        assertTrue(passwordEncoder.matches("test0", profile.getPassword()));
        assertEquals("test0@gmail.com", profile.getEmail());
        assertEquals(1, profile.getRegisterStage());
    }

    // --------------- For verification Code is incorrect ---------------
    @Test
    public void tesVerifyUserVerificationCode() throws Exception {
        setUpSendVerification("test0", "test0", "test0@gmail.com", "ABCD09");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        requestBody.put("verificationCode", "777777");
        
        mockMvc.perform(post("/api/register/verify")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Verification Code is incorrect")
        );
    }

    // --------------- For username doesn't exist ---------------
    @Test
    public void testerifyUserName() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        requestBody.put("verificationCode", "777777");
        
        mockMvc.perform(post("/api/register/verify")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Username doesn't exist")
        );
    }

    // --------------- For email hasn't been registered ---------------
    @Test
    public void testVerifyEmail() throws Exception {
        setUpPersonalInfo("test1", "test1");
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userName", "test1");
        requestBody.put("verificationCode", "777777");
        
        mockMvc.perform(post("/api/register/verify")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Email hasn't been registered")
        );
    }

    // =============== /api/register/preference ===============
    // --------------- For preference saved successfully ---------------
    @Test
    public void testPreference() throws Exception {
        setUpInfo("test0", "test0@gmail.com", 1);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        List<Object> jobTitles = new ArrayList<>();
        jobTitles.add("Backend Engineer");
        jobTitles.add("Frontend Engineer");
        requestBody.put("desiredJobsTitle", jobTitles);
        List<Object> locations = new ArrayList<>();
        locations.add("Taiwan");
        locations.add("America");
        requestBody.put("desiredJobsLocation", locations);
        List<Object> skills = new ArrayList<>();
        skills.add("Backend Engineer");
        skills.add("Frontend Engineer");
        requestBody.put("skills", skills);
        List<Object> companies = new ArrayList<>();
        companies.add("Google");
        companies.add("Microsoft");
        requestBody.put("companies", companies);

        
        mockMvc.perform(post("/api/register/preference")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(content().string("Preference saved successfully")
        );

        Profile profile = profileRepository.findByUserName("test0");
        List<Company> company = companyRepository.findByUserId(profile.getId());
        List<String> companyNames = new ArrayList<>();
        for (Company c : company) {
            companyNames.add(c.getName()); // 假設名稱存儲在 name 屬性中
        }

        assertNotNull(profile);
        assertEquals("test0", profile.getUserName());
        assertEquals("test0@gmail.com", profile.getEmail());
        assertEquals(2, profile.getRegisterStage());
        assertEquals(skills, profile.getSkills());
        assertEquals(locations, profile.getDesiredJobsLocation());
        assertEquals(jobTitles, profile.getDesiredJobsTitle());
        assertEquals(companies, companyNames);
    }

    // --------------- For userName doesn't exist ---------------
    @Test
    public void testPreferenceUserName() throws Exception {
        // setUpInfo("test0", "test0@gmail.com", 1);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        List<Object> jobTitles = new ArrayList<>();
        jobTitles.add("Backend Engineer");
        jobTitles.add("Frontend Engineer");
        requestBody.put("desiredJobsTitle", jobTitles);
        List<Object> locations = new ArrayList<>();
        locations.add("Taiwan");
        locations.add("America");
        requestBody.put("desiredJobsLocation", locations);
        List<Object> skills = new ArrayList<>();
        skills.add("Backend Engineer");
        skills.add("Frontend Engineer");
        requestBody.put("skills", skills);
        List<Object> companies = new ArrayList<>();
        companies.add("Google");
        companies.add("Microsoft");
        requestBody.put("companies", companies);

        
        mockMvc.perform(post("/api/register/preference")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isForbidden())
            .andExpect(content().string("Username doesn't exist")
        );
    }

        // --------------- For userName doesn't exist ---------------
    @Test
    public void testPreferenceStage() throws Exception {
        setUpInfo("test0", "test0@gmail.com", 2);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userName", "test0");
        List<Object> jobTitles = new ArrayList<>();
        jobTitles.add("Backend Engineer");
        jobTitles.add("Frontend Engineer");
        requestBody.put("desiredJobsTitle", jobTitles);
        List<Object> locations = new ArrayList<>();
        locations.add("Taiwan");
        locations.add("America");
        requestBody.put("desiredJobsLocation", locations);
        List<Object> skills = new ArrayList<>();
        skills.add("Backend Engineer");
        skills.add("Frontend Engineer");
        requestBody.put("skills", skills);
        List<Object> companies = new ArrayList<>();
        companies.add("Google");
        companies.add("Microsoft");
        requestBody.put("companies", companies);

        
        mockMvc.perform(post("/api/register/preference")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Stage incorrect")
        );
    }
}
