package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import JobHunting.service.*;

@RestController
@RequestMapping("/api/register")

public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping(value = "/personalInfo", consumes = "application/json") 
    public ResponseEntity<String> createPersonalInfo(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String password = body.get("password");
        try {
            String res = registerService.registerPersonalInfo(userName, password);
            if (res == "Username has already been registered"){
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/sendVerification", consumes = "application/json") 
    public ResponseEntity<String> sendVerificationEmail(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String email = body.get("email");
        try {
            String res = registerService.registerSendVerificationCode(userName, email);
            if (res == "Username doesn't exist"){
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            } else if (res == "Email address has already been registered"){
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/verify", consumes = "application/json")
    public ResponseEntity<String> checkVerificationCode(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String verificationCode = body.get("verificationCode");
        try {
            String res = registerService.verifyCode(userName, verificationCode);
            if (res == "Username doesn't exist") {
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            } else if (res == "Email hasn't been registered") {
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            } else if (res == "Verification Code is incorrect") {
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/preference", consumes = "application/json")
    public ResponseEntity<String> createPreference(@RequestBody Map<String, Object> body) {
        String userName = (String) body.get("userName");
        List<String> desiredJobsTitle = castToList(body.get("desiredJobsTitle"));
        List<String> desiredJobsLocation = castToList(body.get("desiredJobsLocation"));
        List<String> skills = castToList(body.get("skills"));
        List<String> companies = castToList(body.get("companies"));

        try {
            String res = registerService.registerPreference(userName, desiredJobsTitle, desiredJobsLocation, skills, companies);
            if (res == "Username doesn't exist"){
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            } else if (res == "Stage incorrect"){
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> castToList(Object obj) {
        return Optional.ofNullable(obj)
                .filter(List.class::isInstance)
                .map(List.class::cast)
                .orElse(Collections.emptyList());
    }
}


// OK (200): 請求成功。
// CREATED (201): 已創建，常用於 POST。
// ACCEPTED (202): 已接受，但尚未處理完成。
// NO_CONTENT (204): server 成功處理請求，但不需要 return 任何實體內容。
// BAD_REQUEST (400): client 發送的 request 有誤。
// UNAUTHORIZED (401): 使用者需要驗證身分。
// FORBIDDEN (403): server 理解 request，但拒絕服務，我就任性。
// NOT_FOUND (404): server 無法找到 request 的資源。
// METHOD_NOT_ALLOWED (405): request 中指定的方法不被允許。
// INTERNAL_SERVER_ERROR (500): server 內部錯誤，code 寫爛了 QQ。
