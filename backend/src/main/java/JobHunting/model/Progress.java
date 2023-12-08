package JobHunting.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "progress")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Progress {
    @Id
    private ObjectId _id;
    private int progressId;
    private int userId;
    private String companyName;
    private String jobTitle;
    private List<String> stages;
    private List<Date> dates;
    private List<int> stageStatus;

    public void setId(int progressId) {
        this.progressId = progressId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCompanyname(String companyName) {
        this.companyName = companyName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void initStages(String stage, Date date, int stageStatus) {
        this.stages = new ArrayList<String>();
        this.stages.add(stage);
        this.dates = new ArrayList<Date>();
        this.dates.add(date);
        this.stageStatus = new ArrayList<int>();
        this.stageStatus.add(stageStatus);
    }

    public void addStage(String stageName, Date date, int status) {
        this.stages.add(stageName);
        this.dates.add(date);
        this.status.add(status);
    }

    public void setStage(int index, String stageName, Date date, int status) {
        this.stages[index] = stageName;
        this.dates[index] = date;
        this.stageStatus[index] = status;
    }
}