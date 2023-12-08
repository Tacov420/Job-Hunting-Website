package JobHunting.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
// import java.util.*;

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
    private List<LocalDate> dates;
    private List<Integer> stageStatus;

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

    public void initStages(String stage, LocalDate date, int stageStatus) {
        this.stages = new ArrayList<String>();
        this.stages.add(stage);
        this.dates = new ArrayList<LocalDate>();
        this.dates.add(date);
        this.stageStatus = new ArrayList<Integer>();
        this.stageStatus.add(stageStatus);
    }

    public void addStage(String stageName, LocalDate date, int status) {
        this.stages.add(stageName);
        this.dates.add(date);
        this.stageStatus.add(status);
    }

    public void setStage(int index, String stageName, LocalDate date, int status) {
        this.stages.set(index, stageName);
        this.dates.set(index, date);
        this.stageStatus.set(index, status);
    }

}