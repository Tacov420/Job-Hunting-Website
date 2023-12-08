package JobHunting.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private ObjectId _id;
    private String userName;
    private String message;
    private String notificationType;
    private Date date;
    private boolean sent = false;
    private boolean isRead = false;
    private String companyName;
    private int notificationId;

    // for notification
    public void setNotification(int notificationId, String userName, String message, String notificationType,
            Date date, boolean sent,
            boolean isRead, String companyName) {
        this.notificationId = notificationId;
        this.userName = userName;
        this.message = message;
        this.notificationType = notificationType;
        this.date = date;
        this.sent = sent;
        this.isRead = isRead;
        this.companyName = companyName;
    }

    public void getUserName(String userName) {
        this.userName = userName;
    }

    public void getNotificationByUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return this.notificationId;
    }

    // Field to store the job ID
    private ObjectId jobId;

    public ObjectId getJobId() {
        return this.jobId;
    }

    public void setJobId(ObjectId jobId) {
        this.jobId = jobId;
    }
}
