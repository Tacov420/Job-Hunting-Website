// package JobHunting.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.boot.context.config.Profiles;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import JobHunting.model.PreferenceDTO;
// //import JobHunting.model.ProfileDTO;
// import JobHunting.model.Profile;
// import JobHunting.service.ProfileService;
// import JobHunting.service.ProfileService.PreferencesNotFilledException;
// import JobHunting.service.ProfileService.UserNotFoundException;

// @RestController
// @RequestMapping("/api/profile")
// @CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求

// public class PreferenceController {
// @Autowired
// private ProfileService profileService;

// // Method to get preferences
// @GetMapping("/preference/{userName}")
// public ResponseEntity<?> getPreference(@PathVariable String userName) {
// PreferenceDTO preference = profileService.getPreference(userName);
// if (userName == null) {
// throw profileService.new UserNotFoundException("User not found for username:
// " + userName);
// }

// return ResponseEntity.ok(preference);
// }

// // Update preference
// @PutMapping("/preference/{userName}")
// public ResponseEntity<?> updatePreference(@PathVariable String userName,
// @RequestBody Profile updatedProfile) {
// Profile profile = profileService.updatePreference(userName, updatedProfile);
// if (profile == null) {
// throw profileService.new UserNotFoundException("User not found for username:
// " + userName);
// }
// return ResponseEntity.ok(profile);
// }

// @ExceptionHandler(UserNotFoundException.class)
// public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
// return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
// }

// @ExceptionHandler(PreferencesNotFilledException.class)
// public ResponseEntity<?>
// handlePreferencesNotFilled(PreferencesNotFilledException ex) {
// return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
// }

// }
