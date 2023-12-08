package JobHunting.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
// import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Field;

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
    private int jobId;
    private String userName;

    // for notification
    private boolean notificationSent = false;

    private int id;

    public int getId() {
        return this.id;
    }

    private int companyId;
    private String companyName;

    public int getCompanyId() {
        return this.companyId;
    }

}
