package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import JobHunting.dto.*;
import JobHunting.model.*;
import JobHunting.repository.*;
import JobHunting.service.*;
import JobHunting.service.ProfileService.UserNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Profile setProfile(String userName, String Email) {
        Profile profile = new Profile();
        profile.setUserName(userName);
        profile.setEmail(Email);
        return profile;
    }

    public Profile setPreference(List<String> desiredJobsTitle, List<String> desiredJobsLocation, List<String> skills) {
        Profile profile = new Profile();
        profile.setDesiredJobsLocation(desiredJobsLocation);
        profile.setDesiredJobsTitle(desiredJobsTitle);
        profile.setSkills(skills);
        return profile;
    }

    @Test
    public void testGetProfile() {
        when(profileRepository.findByUserName("test0")).thenReturn(null);
        Exception exception = assertThrows(Exception.class, () -> {
            profileService.getProfileByUserName("test0");
        });
        assertTrue(exception instanceof UserNotFoundException);
        if (exception instanceof UserNotFoundException) {
            assertEquals("User not found for username: test0", exception.getMessage());
        }
        Profile profile = setProfile("test0", "test0@gmail.com");
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        ProfileDTO result = profileService.getProfileByUserName("test0");
        assertEquals("test0", result.getUserName());
        assertEquals("test0@gmail.com", result.getEmail());
    }

    @Test
    public void testGetPreference() {
        when(profileRepository.findByUserName("test0")).thenReturn(null);
        Exception exception = assertThrows(Exception.class, () -> {
            profileService.getProfileByUserName("test0");
        });
        assertTrue(exception instanceof UserNotFoundException);
        if (exception instanceof UserNotFoundException) {
            assertEquals("User not found for username: test0", exception.getMessage());
        }
        Profile profile = setPreference(Arrays.asList("Q", "W"), Arrays.asList("E", "R"), Arrays.asList("D", "F"));
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        PreferenceDTO result = profileService.getPreferenceByUserName("test0");
        assertEquals(Arrays.asList("E", "R"), result.getDesiredJobsLocation());
        assertEquals(Arrays.asList("Q", "W"), result.getDesiredJobsTitle());
        assertEquals(Arrays.asList("D", "F"), result.getSkills());
    }
}
