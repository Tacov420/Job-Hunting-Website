package JobHunting.dto;

import java.util.List;

public class PreferenceDTO {
    private List<String> desiredJobsTitle;
    private List<String> desiredJobsLocation;
    private List<String> skills;

    // Constructor, getters, and setters
    public PreferenceDTO(List<String> desiredJobsTitle, List<String> desiredJobsLocation, List<String> skills) {
        this.desiredJobsTitle = desiredJobsTitle;
        this.desiredJobsLocation = desiredJobsLocation;
        this.skills = skills;
    }

    // Getters and setters
    public List<String> getDesiredJobsTitle() {
        return desiredJobsTitle;
    }

    public List<String> getDesiredJobsLocation() {
        return desiredJobsLocation;
    }

    public List<String> getSkills() {
        return skills;
    }
}
