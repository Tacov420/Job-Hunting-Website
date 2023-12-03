package JobHunting.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobHunting.model.ProfileDTO;
import JobHunting.service.ProfileService;
import JobHunting.service.ProfileService.PasswordMismatchException;
import JobHunting.service.ProfileService.UserNotFoundException;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求

public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePasswordMismatch(PasswordMismatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateProfile(@PathVariable String userName,
            @RequestBody Map<String, Object> requestBody) {
        // Extract the new password and confirmation password from the request body
        String newPassword = (String) requestBody.get("newPassword");
        String confirmPassword = (String) requestBody.get("confirmPassword");

        if (newPassword != null && confirmPassword != null) {
            if (!newPassword.equals(confirmPassword)) {
                // The passwords do not match, return an appropriate error message
                return new ResponseEntity<>("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
            }

            // Call the service layer to update the password
            String passwordUpdateResponse = profileService.updatePassword(userName, newPassword, confirmPassword);
            if (!passwordUpdateResponse.equals("Password updated successfully")) {
                // The password update failed, return the error message from the service
                return new ResponseEntity<>(passwordUpdateResponse, HttpStatus.BAD_REQUEST);
            }
        } else {
            // If newPassword or confirmPassword is null, return an appropriate message
            return new ResponseEntity<>("Password cannot be null", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Profile Personal Information updated successfully", HttpStatus.OK);
    }

    // Get profile
    @GetMapping("/{userName}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable("userName") String userName) {
        ProfileDTO profileDTO = profileService.getProfile(userName);
        if (profileDTO != null) {
            return new ResponseEntity<>(profileDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}