package JobHunting.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "profile")
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
    private int companyId;

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

    // Preference
    public void setPreference(List<String> desiredJobsTitle, List<String> desiredJobsLocation, List<String> skills,
            int registerStage) {

        this.desiredJobsTitle = desiredJobsTitle;
        this.desiredJobsLocation = desiredJobsLocation;
        this.skills = skills;
        this.registerStage = registerStage;
    }

    public void setStage(int registerStage) {
        this.registerStage = registerStage;
    }

}
