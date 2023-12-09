package JobHunting.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private ObjectId _id;
    private int notificationId;
    private int userId;
    private LocalDate date;
    private String content;
    private boolean isRead = false;

    public void setNotification(int notificationId, int userId, LocalDate date, String content) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.date = date;
        this.content = content;
    }

    public boolean getIsRead() {
        return this.isRead;
    }

    public void readNotification() {
        this.isRead = true;
    }
}
