package JobHunting.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
// import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

@Document(collection = "jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    private ObjectId _id;
    private String company;
    @Field("job-title")
    private String jobTitle;
    private String level;

    // for notification
    private Date postingDate; // Date the job was posted
    private String location; // Job location
    private boolean notificationSent; // Flag to check if notification is sent

}
