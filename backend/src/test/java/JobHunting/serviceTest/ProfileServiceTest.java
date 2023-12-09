package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
            profileService.getPreferenceByUserName("test0");
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

    @Test
    public void testUpdateProfile() {
        when(profileRepository.findByUserName("test0")).thenReturn(null);
        Exception exception = assertThrows(Exception.class, () -> {
            profileService.updatePassword("test0", "88", "88");
        });
        assertTrue(exception instanceof UserNotFoundException);
        if (exception instanceof UserNotFoundException) {
            assertEquals("Username hasn't been registered: test0", exception.getMessage());
        }
        Profile profile = setProfile("test0", "test0@gmail.com");
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        String result = profileService.updatePassword("test0", "88", "88");
        assertEquals("Password updated successfully", result);
    }
    
    @Test
    public void testUpdatePreference() {
        when(profileRepository.findByUserName("test0")).thenReturn(null);
        Profile profile = setPreference(Arrays.asList("Q", "W"), Arrays.asList("E", "R"), Arrays.asList("D", "F"));
        Exception exception = assertThrows(Exception.class, () -> {
            profileService.updatePreference("test0", profile);
        });
        assertTrue(exception instanceof UserNotFoundException);
        if (exception instanceof UserNotFoundException) {
            assertEquals("Username hasn't been registered: test0", exception.getMessage());
        }
        Profile profile1 = setPreference(Arrays.asList("D", "F"), Arrays.asList("Q", "W"), Arrays.asList("E", "R"));
        when(profileRepository.findByUserName("test0")).thenReturn(profile);
        String result = profileService.updatePreference("test0",profile1);
        assertEquals("Update preference successfully", result);
    }   
}
