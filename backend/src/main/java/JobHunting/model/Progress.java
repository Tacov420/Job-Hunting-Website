package JobHunting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "progress")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Progress {
    @Id
    private ObjectId id;
    private int progressId;
    private String userName;
    private List<String> companyName;
    private List<String> jobsTitle;
    private int progressStage; // 0: Applied, 1: First Interview, 2: Second Interview,3:Offer,4:Rejected
    private List<String> newProgressStage;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateApplied;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFirstInterview;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateSecondInterview;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOffer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateRejected;

    // setters

    public String getId() {

        return id.toHexString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public void setProgress(int progressId, String userName, List<String> companyName,
            List<String> jobsTitle, int progressStage) {
        this.progressId = progressId;
        this.userName = userName;
        this.companyName = companyName;
        this.jobsTitle = jobsTitle;
        this.progressStage = progressStage;
        this.createdDate = new Date();
    }

    public void setNewProgressStage(List<String> newProgressStage, int progressStage, int progressId,
            String userName) {
        this.newProgressStage = newProgressStage;
        this.progressStage = progressStage;
        this.progressId = progressId;
        this.userName = userName;

    }

    public void addCompanyName(String companyName) {
        if (this.companyName == null) {
            this.companyName = new ArrayList<>();
        }
        this.companyName.add(companyName);
    }

    public void addJobsTitle(String jobsTitle) {
        if (this.jobsTitle == null) {
            this.jobsTitle = new ArrayList<>();
        }
        this.jobsTitle.add(jobsTitle);
    }

    // remove

    public void removeCompanyName(String companyName) {
        if (this.companyName != null) {
            this.companyName.remove(companyName);
        }
    }

    public void removeJobsTitle(String jobsTitle) {
        if (this.jobsTitle != null) {
            this.jobsTitle.remove(jobsTitle);
        }
    }

    // update

    public enum UpdateType {
        PROGRESS_STAGE,
        COMPANY_NAME,
        JOB_TITLE,
        CREATED_DATE,
        LAST_MODIFIED_DATE,
        DATE_APPLIED,
        DATE_FIRST_INTERVIEW,
        DATE_SECOND_INTERVIEW,
        DATE_OFFER,
        DATE_REJECTED

    }

    public void updateFrom(Map<String, Object> fields) {
        if (fields != null) {
            // other fields...
            if (fields.containsKey("userName")) {
                this.userName = (String) fields.get("userName");
            }
            if (fields.containsKey("jobsTitle")) {
                Object jobsTitleObj = fields.get("jobsTitle");
                if (jobsTitleObj instanceof List<?>) {
                    List<?> rawList = (List<?>) jobsTitleObj;
                    if (rawList.isEmpty() || rawList.get(0) instanceof String) {
                        List<String> stringList = rawList.stream().map(Object::toString).collect(Collectors.toList());
                        this.jobsTitle = stringList;
                    } else {
                        throw new IllegalArgumentException("Not all elements in jobsTitle are of type String");
                    }
                } else {
                    throw new IllegalArgumentException("jobsTitle field is not of type List");
                }
            }
            if (fields.containsKey("progressStage")) {

                this.progressStage = (int) fields.get("progressStage");
            }
            if (fields.containsKey("companyName")) {
                Object companyName = fields.get("companyName");
                if (companyName instanceof List<?>) {
                    List<?> rawList = (List<?>) companyName;
                    if (rawList.isEmpty() || rawList.get(0) instanceof String) {
                        List<? extends String> stringList = rawList.stream().map(Object::toString)
                                .collect(Collectors.toList());
                        this.companyName = new ArrayList<>(stringList);
                    } else {
                        throw new IllegalArgumentException("Not all elements in companyTitle are oft type String");

                    }
                } else {
                    throw new IllegalArgumentException("company field is not of type List");
                }
            }

            if (fields.containsKey("createdDate")) {
                this.createdDate = (Date) fields.get("createdDate");
            }
            if (fields.containsKey("lastModifiedDate")) {
                this.lastModifiedDate = (Date) fields.get("lastModifiedDate");
            }

            if (fields.containsKey("dateApplied")) {
                this.dateApplied = (Date) fields.get("dateApplied");
            }

            if (fields.containsKey("dateFirstInterview")) {
                this.dateFirstInterview = (Date) fields.get("dateFirstInterview");
            }

            if (fields.containsKey("dateSecondInterview")) {
                this.dateSecondInterview = (Date) fields.get("dateSecondInterview");
            }

            if (fields.containsKey("dateOffer")) {
                this.dateOffer = (Date) fields.get("dateOffer");
            }

            if (fields.containsKey("dateRejected")) {
                this.dateRejected = (Date) fields.get("dateRejected");
            }
        }
    }

}