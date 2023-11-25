package JobHunting.service;

// import java.util.List;
// import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.PathVariable;

import JobHunting.repository.*;
import JobHunting.service.ProfileService;
import JobHunting.model.Profile;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Profile getProfile(String userName) {
        return profileRepository.findProfileByUserName(userName);
    }

    public Profile updateProfile(String userName, String password, Profile updatedProfile, String confirmPassword,
            String newPassword) {
        Profile existingProfile = profileRepository.findProfileByUserName(userName);
        if (existingProfile != null) {
            // Check if the new password and confirm password match
            if (passwordEncoder.matches(confirmPassword, existingProfile.getPassword()) &&
                    !updatedProfile.getPassword().equals(confirmPassword)) {
                // Encrypt and set the new password
                existingProfile.setPassword(passwordEncoder.encode(updatedProfile.getPassword()));
                return profileRepository.save(existingProfile);
            }
            // Return null or handle the error if passwords do not match
        }
        return null; // or throw an appropriate exception
    }

    public String updatePassword(String userName, String newPassword, String confirmPassword) {
        Profile existingProfile = profileRepository.findProfileByUserName(userName);
        if (existingProfile != null) {
            // Check if the new password and confirm password match
            if (newPassword.equals(confirmPassword)) {
                // Encrypt and set the new password
                existingProfile.setPassword(passwordEncoder.encode(newPassword));
                profileRepository.save(existingProfile);
                return "Success";
            }
            return "New password and confirm password do not match";
        }
        return "Username hasn't been registered";
    }

    public Profile updatePreference(String userName, Profile updatedProfile) {
        Profile existingProfile = profileRepository.findProfileByUserName(userName);

        if (existingProfile != null) {
            // Update preference fields
            existingProfile.setDesiredJobsTitle(updatedProfile.getDesiredJobsTitle());
            existingProfile.setDesiredJobsLocation(updatedProfile.getDesiredJobsLocation());
            existingProfile.setSkills(updatedProfile.getSkills());

            // Save the updated profile
            return profileRepository.save(existingProfile);
        }
        return null;
    }

}
