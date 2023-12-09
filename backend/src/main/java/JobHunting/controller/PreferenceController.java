package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobHunting.model.Profile;
import JobHunting.dto.*;
import JobHunting.service.ProfileService;
import JobHunting.service.ProfileService.PreferencesNotFilledException;
import JobHunting.service.ProfileService.UserNotFoundException;

@RestController
@RequestMapping("/api/profile")
public class PreferenceController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/preference/{userName}")
    public ResponseEntity<?> getPreference(@PathVariable String userName) {
        if (userName == null) {
            return new ResponseEntity<>("User not found for username: " + userName, HttpStatus.BAD_REQUEST);
        }
        try {
            PreferenceDTO preference = profileService.getPreferenceByUserName(userName);
            return ResponseEntity.ok(preference);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/preference/{userName}")
    public ResponseEntity<?> updatePreference(@PathVariable String userName,
            @RequestBody Profile updatedPreference) {
        try {
            String res = profileService.updatePreference(userName, updatedPreference);
            return ResponseEntity.ok(res);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (PreferencesNotFilledException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Log the exception details to help with debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating preferences.");
        }
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PreferencesNotFilledException.class)
    public ResponseEntity<?> handlePreferencesNotFilled(PreferencesNotFilledException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
