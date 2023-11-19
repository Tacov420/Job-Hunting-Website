package JobHunting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // --------------- For first time register ---------------
    @Test
    public void testRegisterUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"userName\": \"test0\", \"password\": \"test0\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject("/api/register/personalInfo", requestEntity, String.class);

        assertEquals("Personal info registered successfully", response);
    }

	@Test
    public void testRegisterUser1() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"userName\": \"test1\", \"password\": \"test1\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject("/api/register/personalInfo", requestEntity, String.class);

        assertEquals("Personal info registered successfully", response);
    }

    // --------------- For repeate username register ---------------
    @Test
    public void testRegisterRepeatUserName() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"userName\": \"test0\", \"password\": \"balabalabala\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject("/api/register/personalInfo", requestEntity, String.class);

        assertEquals("Username has already been registered", response);
    }

	// --------------- For normal register preference ---------------
    @Test
    public void testRegisterSendVerificationCode() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"userName\": \"testUser\", \"email\": \"b09902074@ntu.edu.tw\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject("/api/register/preference", requestEntity, String.class);

        assertEquals("Send Email successfully", response);
    }


    // --------------- For normal register preference ---------------
    @Test
    public void testRegisterPreference() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"userName\": \"testUser\", \"desiredJobsTitle\": \"[\"77777\", \"8888888\"]\", \"desiredJobsLocation\": \"[\"7\", \"8\"]\", \"skills\": \"[\"SLEEPING\", \"SLEEPING2\"]\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject("/api/register/preference", requestEntity, String.class);

        assertEquals("Preference registered successfully", response);
    }

    // --------------- For abnormal register preference ---------------
    @Test
    public void testAbnormalRegisterPreference() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"userName\": \"testUser8\", \"desiredJobsTitle\": \"[\"77777\", \"8888888\"]\", \"desiredJobsLocation\": \"[\"7\", \"8\"]\", \"skills\": \"[\"SLEEPING\", \"SLEEPING2\"]\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject("/api/register/preference", requestEntity, String.class);

        assertEquals("Cannot find the username", response);
    }

    // --------------- For login successfully ---------------
    @Test
    public void testLogin() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"userName\": \"testUser\", \"password\": \"password\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject("/api/login", requestEntity, String.class);

        assertEquals("Cannot find the username", response);
    }

}
