package JobHunting.dto;

import org.bson.types.ObjectId;

public class ProfileDTO {
    private ObjectId _id;
    private int id;
    private String userName;
    private String email;
    private int registerStage;

    // Constructor, getters, and setters
    public ProfileDTO(ObjectId _id, int id, String userName, String email, int registerStage) {
        this._id = _id;
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.registerStage = registerStage;
    }

    // Getters and setters
    public ObjectId get_id() {
        return _id;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getRegisterStage() {
        return registerStage;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }
}
