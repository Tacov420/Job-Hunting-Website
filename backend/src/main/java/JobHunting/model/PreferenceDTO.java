package JobHunting.model;

import java.util.List;

public class PreferenceDTO {
    private List<String> desiredJobsTitle;
    private List<String> desiredJobsLocation;
    private List<String> skills;
    private String userName;

    // Constructor, Getters, and Setters

    public PreferenceDTO(List<String> desiredJobsTitle, List<String> desiredJobsLocation,
            List<String> skills) {
        this.desiredJobsTitle = desiredJobsTitle;
        this.desiredJobsLocation = desiredJobsLocation;
        this.skills = skills;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getDesiredJobsTitle() {
        return desiredJobsTitle;
    }

    public void setDesiredJobsTitle(List<String> desiredJobsTitle) {
        this.desiredJobsTitle = desiredJobsTitle;
    }

    public List<String> getDesiredJobsLocation() {
        return desiredJobsLocation;
    }

    public void setDesiredJobsLocation(List<String> desiredJobsLocation) {
        this.desiredJobsLocation = desiredJobsLocation;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    // Getters and setters for desiredJobsTitle, desiredJobsLocation, and skills
    // ...
}
