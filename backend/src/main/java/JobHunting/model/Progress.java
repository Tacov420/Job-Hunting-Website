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

    public void setProgressId(int progressId) {
        this.progressId = progressId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCompanyName(List<String> companyName) {
        this.companyName = companyName;
    }

    public void setJobsTitle(List<String> jobsTitle) {
        this.jobsTitle = jobsTitle;
    }

    public void setProgressStage(int progressStage) {
        this.progressStage = progressStage;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setDateApplied(Date dateApplied) {
        this.dateApplied = dateApplied;
    }

    public void setDateFirstInterview(Date dateFirstInterview) {
        this.dateFirstInterview = dateFirstInterview;
    }

    public void setDateSecondInterview(Date dateSecondInterview) {
        this.dateSecondInterview = dateSecondInterview;
    }

    public void setDateOffer(Date dateOffer) {
        this.dateOffer = dateOffer;
    }

    public void setDateRejected(Date dateRejected) {
        this.dateRejected = dateRejected;
    }

    // setters

    // getters
    public void getProgressId(int progressId) {
        this.progressId = progressId;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getCompanyName() {
        return companyName;
    }

    public List<String> getJobsTitle() {
        return jobsTitle;
    }

    public int getProgressStage() {
        return progressStage;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Date getDateApplied() {
        return dateApplied;
    }

    public Date getDateFirstInterview() {
        return dateFirstInterview;
    }

    public Date getDateSecondInterview() {
        return dateSecondInterview;
    }

    public Date getDateOffer() {
        return dateOffer;
    }

    public Date getDateRejected() {
        return dateRejected;
    }

    // add

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

    public void addProgressStage(int progressStage) {
        this.progressStage = progressStage;
    }

    public void addCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void addLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void addDateApplied(Date dateApplied) {
        this.dateApplied = dateApplied;
    }

    public void addDateFirstInterview(Date dateFirstInterview) {
        this.dateFirstInterview = dateFirstInterview;
    }

    public void addDateSecondInterview(Date dateSecondInterview) {
        this.dateSecondInterview = dateSecondInterview;
    }

    public void addDateOffer(Date dateOffer) {
        this.dateOffer = dateOffer;
    }

    public void addDateRejected(Date dateRejected) {
        this.dateRejected = dateRejected;
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

    public void removeProgressStage(int progressStage) {
        this.progressStage = progressStage;
    }

    public void removeCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void removeLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void removeDateApplied(Date dateApplied) {
        this.dateApplied = dateApplied;
    }

    public void removeDateFirstInterview(Date dateFirstInterview) {
        this.dateFirstInterview = dateFirstInterview;
    }

    public void removeDateSecondInterview(Date dateSecondInterview) {
        this.dateSecondInterview = dateSecondInterview;
    }

    public void removeDateOffer(Date dateOffer) {
        this.dateOffer = dateOffer;
    }

    public void removeDateRejected(Date dateRejected) {
        this.dateRejected = dateRejected;
    }

    public void removeProgressId(int progressId) {
        this.progressId = progressId;
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