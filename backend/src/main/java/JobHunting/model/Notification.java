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
    private ObjectId _id;
    private int notificationId;
    private String userName;
    private String message;
    private String notificationType; // e.g., "CompanyUpdate", "InterviewReminder"
    private Date date;
    private boolean sent = false; // Flag to check if notification is sent

    public void setNotification(int notificationId, String userName, String message,
            String notificationType, Date date) {
        this.notificationId = notificationId;
        this.userName = userName;
        this.message = message;
        this.notificationType = notificationType;
        this.date = date;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void getNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public void getSent(boolean sent) {
        this.sent = sent;
    }

    public void getDate(Date date) {
        this.date = date;
    }

    public void getNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void getMessage(String message) {
        this.message = message;
    }

}
