package JobHunting.dto;

public class ProfileDTO {
    private String userName;
    private String email;

    // Constructor, getters, and setters
    public ProfileDTO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    // Getters and setters
    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }
}
