package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
// import java.util.List;

import JobHunting.model.Profile;
import JobHunting.repository.ProfileRepository;

@Service
public class LoginService {
    @Autowired
    private ProfileRepository profileRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String login(String userName, String password) {
        Profile profile = profileRepository.findProfileByUserName(userName);

        if (profile != null) {
            if (passwordEncoder.matches(password, profile.getPassword())) {
                if (profile.getRegisterStage() == 2) {
                    return "Login successfully";
                } else if (profile.getRegisterStage() == 1) {
                    return "Hasn't filled in preference";
                } else if (profile.getRegisterStage() == 0) {
                    return "Hasn't verified";
                }
            } else {
                return "Password is incorrect";
            }
        }
        return "Username hasn't been registered";
    }
}
