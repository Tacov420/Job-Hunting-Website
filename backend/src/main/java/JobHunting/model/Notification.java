package JobHunting.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notification")
public class Notification {
    @Id
    private ObjectId id; // Use ObjectId for MongoDB IDs
    private String userName;
    private String message;
    private String notificationType;
    private Date date;
    private boolean sent = false;
    private boolean isRead = false; // Default value is false

    public Notification(String userName, String message, String notificationType) {
        this.userName = userName;
        this.message = message;
        this.notificationType = notificationType;
        this.date = new Date();
        this.sent = false;
        this.isRead = false;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

}
