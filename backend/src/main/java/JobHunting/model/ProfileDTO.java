package JobHunting.model;

public class ProfileDTO {
    private String userName;
    private String email;

    // Constructor
    public ProfileDTO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString(), hashCode(), equals(), etc. can be overridden as needed
}

