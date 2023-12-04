package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import JobHunting.dto.ProfileDTO;
import JobHunting.model.Profile;
import JobHunting.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Method to get profile
    public ProfileDTO getProfileByUserName(String userName) {
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile == null) {
            throw new UserNotFoundException("User not found for username: " + userName);
        }
        // Convert the Profile entity to ProfileDTO before returning
        return new ProfileDTO(profile.get_id(), profile.getId(), profile.getUserName(), profile.getEmail(),
                profile.getRegisterStage());
    }

    // Service method called from the controller
    public Profile getPreferenceByUserName(String userName) {
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile == null) {
            throw new UserNotFoundException("User not found for username: " + userName);
        }
        Profile preference = profileRepository.findPreferenceByUserName(userName);
        if (preference == null) {
            // Handle the case where no preference exists for the user
            preference = new Profile();
            preference.setUserName(userName); // Set the username for the new preference
        }
        return preference;

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

    public Profile updatePreference(String userName, Profile updatedPreference) {
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile == null) {
            throw new UserNotFoundException("Username hasn't been registered: " + userName);
        }

        Profile preference = profileRepository.findPreferenceByUserName(userName);
        if (preference == null) {
            // Handle the case where no preference exists for the user
            preference = new Profile();
            preference.setUserName(userName); // Set the username for the new preference
        }
        if (updatedPreference.getDesiredJobsTitle() == null || updatedPreference.getDesiredJobsLocation() == null
                || updatedPreference.getSkills() == null || updatedPreference.getCompanies() == null) {
            throw new PreferencesNotFilledException("Preferences not filled");
        }

        preference.setDesiredJobsTitle(updatedPreference.getDesiredJobsTitle());
        preference.setDesiredJobsLocation(updatedPreference.getDesiredJobsLocation());
        preference.setSkills(updatedPreference.getSkills());
        preference.setCompanies(updatedPreference.getCompanies());

        return profileRepository.save(preference);
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

}
