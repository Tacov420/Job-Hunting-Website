package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import JobHunting.model.PreferenceDTO;
import JobHunting.model.Profile;
import JobHunting.model.ProfileDTO;
import JobHunting.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Method to get profile
    public ProfileDTO getProfile(String userName) {
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile == null) {
            throw new UserNotFoundException("User not found for username: " + userName);
        }
        return new ProfileDTO(profile.getUserName(), profile.getEmail());
    }

    // Method to get preferences
    public PreferenceDTO getPreference(String userName) {
        if (userName == null) {
            throw new UserNotFoundException("Username cannot be null");
        }

        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile != null) {
            return new PreferenceDTO(profile.getDesiredJobsTitle(),
                    profile.getDesiredJobsLocation(),
                    profile.getSkills());
        } else {
            throw new UserNotFoundException("Username hasn't been registered: " + userName);
        }
    }

    // Method to update password
    public String updatePassword(String userName, String newPassword, String confirmPassword) {
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile == null) {
            throw new UserNotFoundException("Username hasn't been registered: " + userName);
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordMismatchException("New password and confirm password do not match");
        }
        profile.setPassword(passwordEncoder.encode(newPassword));
        profileRepository.save(profile);
        return "Password updated successfully";
    }

    public Profile updatePreference(String userName, Profile updatedProfile) {
        // Fetch the profile from the database
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile == null) {
            throw new UserNotFoundException("Username hasn't been registered: " + userName);
        }

        // Check if the incoming profile has all preferences filled
        if (updatedProfile.getDesiredJobsTitle() == null || updatedProfile.getDesiredJobsLocation() == null
                || updatedProfile.getSkills() == null) {
            throw new PreferencesNotFilledException("Preferences not filled");
        }

        // Update the profile with new preferences
        profile.setDesiredJobsTitle(updatedProfile.getDesiredJobsTitle());
        profile.setDesiredJobsLocation(updatedProfile.getDesiredJobsLocation());
        profile.setSkills(updatedProfile.getSkills());

        // Save the updated profile to the database
        return profileRepository.save(profile);
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public class PasswordMismatchException extends RuntimeException {
        public PasswordMismatchException(String message) {
            super(message);
        }
    }

    public class PreferencesNotFilledException extends RuntimeException {
        public PreferencesNotFilledException(String message) {
            super(message);
        }
    }

    public class PasswordCannotBeNull extends RuntimeException {
        public PasswordCannotBeNull(String message) {
            super(message);
        }
    }

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
            // Return a response entity with the NOT_FOUND status code
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(PasswordMismatchException.class)
        public ResponseEntity<String> handlePasswordMismatchException(PasswordMismatchException ex) {
            // Return a response entity with the BAD_REQUEST status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(PreferencesNotFilledException.class)
        public ResponseEntity<String> handlePreferencesNotFilledException(PreferencesNotFilledException ex) {
            // Return a response entity with the BAD_REQUEST status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(PasswordCannotBeNull.class)
        public ResponseEntity<String> handlePasswordCannotBeNull(PasswordCannotBeNull ex) {
            // Return a response entity with the BAD_REQUEST status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
