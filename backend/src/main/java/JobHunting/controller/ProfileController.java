package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.context.config.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobHunting.service.ProfileService;
import JobHunting.model.Profile;
// import JobHunting.repository.ProfileRepository;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求

public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    // private ProfileRepository profileRepository;

    // Get personal info
    @GetMapping("/{userName}")
    public ResponseEntity<Profile> getProfile(@PathVariable String userName) {
        Profile profile = profileService.getProfile(userName);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateProfile(@PathVariable String userName,
            @RequestBody Map<String, Object> requestBody) {
        // Extract the new password and confirmation password
        String newPassword = (String) requestBody.get("newPassword");
        String confirmPassword = (String) requestBody.get("confirmPassword");

        if (newPassword != null && confirmPassword != null) {
            String passwordUpdateResponse = profileService.updatePassword(userName, newPassword, confirmPassword);
            if (!passwordUpdateResponse.equals("Success")) {
                return new ResponseEntity<>(passwordUpdateResponse, HttpStatus.BAD_REQUEST);
            }
        }

        // Extract and set fields from requestBody to updatedProfile
        Profile updatedProfile = new Profile();
        // ... set fields from requestBody to updatedProfile ...

        // Update other profile details
        Profile profile = profileService.updatePreference(userName, updatedProfile);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }

        return new ResponseEntity<>("Profile not found", HttpStatus.NOT_FOUND);
    }

    // Update preference
    @PutMapping("/{userName}/preference")
    public ResponseEntity<Profile> updatePreference(@PathVariable String userName,
            @RequestBody Profile updatedPreference) {
        Profile profile = profileService.updatePreference(userName, updatedPreference);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

// //Update preference
// @PostMapping(value = "/preference", consumes = "application/json")
// public Profile updatePreference(@RequestBody Map<String, Object> body, String
// userName, Profile updatedPreference, String desiredJobsTitle, String
// desiredJobsLocation, String skills, String companies) {
// Profile existingPreference = profileRepository.findProfileById(userName);

// List<String> desiredJobsTitle = castToList(body.get(desiredJobsTitle));
// List<String> desiredJobsLocation= castToList(body.get(desiredJobsLocation));
// List<String> skills = castToList(body.get(skills));
// List<String> companies = castToList(body.get(companies));

// if (existingPreference != null) {
// // Update the existing profile with new data
// existingPreference.setDesiredJobsTitle(updatedPreference.getDesiredJobsTitle(String[]));
// existingPreference.setDesiredJobsLocation(updatedPreference.getDesiredJobsLocation(String));
// existingPreference.setSkills(updatedPreference.getSkills(String));
// existingPreference.setCompanies(updatedPreference.getCompanies(String));

// return profileRepository.save(existingProfile);
// }
// return null; // or throw an exception
// }

// @PostMapping(value = "/preference", consumes = "application/json")
// public Profile updatePreferenceProfile(@RequestBody Map<String, Object> body,
// String userName, Profile updatedPreference) {

// Profile existingPreferencProfile =
// profileRepository.findProfileByUserName(userName);

// List<String> desiredJobsTitle = castToList(body.get(key: desiredJobsTitle));
// List<String> desiredJobsLocation=
// castToList(body.get("desiredJobsLocation"));
// List<String> skills = castToList(body.get("skills"));
// List<String> companies = castToList(body.get("companies"));

// if (existingPreferencProfile != null) {

// // Update the existing profile with new data
// existingPreferencProfile.setDesiredJobsTitle(updatedPreference.getDesiredJobsTitle(String));
// existingPreferencProfile.setDesiredJobsLocation(updatedPreference.getDesiredJobsLocation(String));
// existingPreferencProfile.setSkills(updatedPreference.getSkills(String));
// existingPreferencProfile.setCompanies(updatedPreference.getCompanies(String));

// //save the updated profile
// return profileRepository.save(existingProfile);
// }
// return null; // or throw an exception

// // try {
// // String pre = profileService.updatePreferenceProfile(desiredJobsTitle,
// desiredJobsLocation, skills, companies);
// // if (res == "Filling is empty"){
// // return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
// // }
// // return new ResponseEntity<>(res, HttpStatus.CREATED);
// // } catch (Exception e) {
// // return new ResponseEntity<>(e.getMessage(),
// HttpStatus.INTERNAL_SERVER_ERROR);
// // }

// }
