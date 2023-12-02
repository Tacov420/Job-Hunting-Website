package JobHunting.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import JobHunting.model.Company;
import JobHunting.model.Profile;
import JobHunting.repository.CompanyRepository;
import JobHunting.repository.ProfileRepository;

@Service
public class RegisterService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerPersonalInfo(String userName, String password) {
        if (profileRepository.findProfileByUserName(userName) != null) {
            return "Username has already been registered";
        }

        int id;
        if (profileRepository.findFirstByOrderByIdDesc() != null) {
            id = profileRepository.findFirstByOrderByIdDesc().getId() + 1;
        } else {
            id = 0;
        }
        Profile profile = new Profile();
        profile.setPersonalInfo(userName, passwordEncoder.encode(password), id, 0);
        profileRepository.save(profile);

        return "Personal info registered successfully";
    }

    public String registerSendVerificationCode(String userName, String email) {
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile == null) {
            return "Username doesn't exist";
        }

        Profile p = profileRepository.findProfileByEmail(email);
        if (p != null) {
            if (!p.getUserName().equals(userName)) {
                return "Email address has already been registered";
            }
        }

        String verificationCode = generateVerificationCode(6);
        profile.setVerificationCode(email, verificationCode, 0);
        profileRepository.save(profile);

        String text = "Hello " + userName + ", welcome to Job Hunting Website!\n\n"
                + "Your 6 characters verification code is: **" + verificationCode
                + "**. Please go back to Job Hunting and enter the verification code.\n\nWishing you a pleasant journey with Job Hunting!";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code from Job Hunting");
        message.setText(text);
        mailSender.send(message);

        return "Send Email successfully";
    }

    public String verifyCode(String userName, String verificationCode) {
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile == null) {
            return "Username doesn't exist";
        }

        String profileCode = profile.getVerificationCode();
        if (profileCode == null) {
            return "Email hasn't been registered";
        }

        if (!verificationCode.equals(profileCode)) {
            return "Verification Code is incorrect";
        }

        profile.setStage(1);
        profileRepository.save(profile);

        return "Verify successfully";
    }

    public String registerPreference(String userName, List<String> desiredJobsTitle, List<String> desiredJobsLocation,
            List<String> skills, List<String> companies) {
        Profile profile = profileRepository.findProfileByUserName(userName);
        if (profile != null) {
            if (profile.getRegisterStage() != 1) {
                return "Stage incorrect";
            }
            profile.setPreference(desiredJobsTitle, desiredJobsLocation, skills, 2);
            profileRepository.save(profile);

            int id;
            if (companyRepository.findFirstByOrderByIdDesc() != null) {
                id = companyRepository.findFirstByOrderByIdDesc().getId() + 1;
            } else {
                id = 0;
            }

            for (String company : companies) {
                Company company_DB = new Company();
                company_DB.setCompany(profile.getId(), company, id, 1);
                companyRepository.save(company_DB);
                id += 1;
            }
            return "Preference saved successfully";
        } else {
            return "Username doesn't exist";
        }
    }

    public static String generateVerificationCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            verificationCode.append(randomChar);
        }

        return verificationCode.toString();
    }
}
