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

    @PostMapping(value = "/personalInfo") 
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

    @PostMapping(value = "/sendVerification") 
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

    @PostMapping(value = "/verify")
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

    @PostMapping(value = "/preference")
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
