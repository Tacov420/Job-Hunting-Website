package JobHunting.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "profile")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Profile {
    @Id
    private ObjectId _id;
    private int id;
    private String userName;
    private String password;
    private String email;
    private List<String> desiredJobsTitle;
    private List<String> desiredJobsLocation;
    private List<String> skills;
    private List<String> companies;

    private int registerStage; // 0: hasn't verified; 1: verify; 2: complete
    private String verificationCode;

    public void setPersonalInfo(String userName, String password, int id, int registerStage) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.registerStage = registerStage;
    }

    public void setVerificationCode(String email, String verificationCode, int registerStage) {
        this.email = email;
        this.registerStage = registerStage;
        this.verificationCode = verificationCode;
    }

    public void setStage(int registerStage) {
        this.registerStage = registerStage;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setRegisterStage(int registerStage) {
        this.registerStage = registerStage;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    public void setProfileId(int id) {
        this.id = id;
    }

    // Preference
    public void setPreference(List<String> desiredJobsTitle, List<String> desiredJobsLocation, List<String> skills,
            int registerStage) {

        this.desiredJobsTitle = desiredJobsTitle;
        this.desiredJobsLocation = desiredJobsLocation;
        this.skills = skills;
        this.registerStage = registerStage;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public String getUserName() {
        return userName;
    }

    public int getId() {
        return id;
    }

    public List<String> getDesiredJobsTitle() {
        return desiredJobsTitle;
    }

    public List<String> getDesiredJobsLocation() {
        return desiredJobsLocation;
    }

    public List<String> getSkills() {
        return skills;
    }

    // set

    public void setDesiredJobsTitle(List<String> desiredJobsTitle) {
        this.desiredJobsTitle = desiredJobsTitle;
    }

    public void setDesiredJobsLocation(List<String> desiredJobsLocation) {
        this.desiredJobsLocation = desiredJobsLocation;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

}
