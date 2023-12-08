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
    private String userName;
    private String message;
    private String notificationType;
    private Date date;
    private boolean sent = false;
    private boolean isRead = false;

    @Id
    private ObjectId notificationId;

    public ObjectId getId() {
        return this.notificationId;
    }

    // Field to store the job ID
    private ObjectId jobId;

    // Getter and setter for jobId
    public ObjectId getJobId() {
        return jobId;
    }

    public void setJobId(ObjectId jobId) {
        this.jobId = jobId;
    }
}
