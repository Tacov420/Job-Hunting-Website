// package JobHunting.model;

// import java.util.ArrayList;
// import java.util.Date;
// import java.util.Map;

// import org.bson.types.ObjectId;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;
// import org.springframework.data.mongodb.core.mapping.Document;

// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// import java.util.List;
// import java.util.stream.Collectors;

// @Document(collection = "progress")
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class Progress {
// @Id
// private ObjectId _id;
// private String userName;
// private List<String> companyName;
// private List<String> jobsTitle;
// private int progressStage; // 0: Applied, 1: First Interview, 2: Second
// Interview, 3: Offer, 4: Rejected

// @CreatedDate
// private Date createdDate;

// @LastModifiedDate
// private Date lastModifiedDate;

// public void setProgress(String userName, List<String> companyName,
// List<String> jobsTitle, int progressStage) {
// this.userName = userName;
// this.companyName = companyName;
// this.jobsTitle = jobsTitle;
// this.progressStage = progressStage;
// }

// public void setUserName(String userName) {
// this.userName = userName;
// }

// public void setCompanyName(List<String> companyName) {
// this.companyName = companyName;
// }

// public void setJobsTitle(List<String> jobsTitle) {
// this.jobsTitle = jobsTitle;
// }

// public void setProgressStage(int progressStage) {
// this.progressStage = progressStage;
// }

// public void setCreatedDate(Date createdDate) {
// this.createdDate = createdDate;
// }

// public void setLastModifiedDate(Date lastModifiedDate) {
// this.lastModifiedDate = lastModifiedDate;
// }

// public void addCompanyName(String companyName) {
// if (this.companyName == null) {
// this.companyName = new ArrayList<>();
// }
// this.companyName.add(companyName);
// }

// public void addJobsTitle(String jobsTitle) {
// if (this.jobsTitle == null) {
// this.jobsTitle = new ArrayList<>();
// }
// this.jobsTitle.add(jobsTitle);
// }

// public void removeCompanyName(String companyName) {
// if (this.companyName != null) {
// this.companyName.remove(companyName);
// }
// }

// public void removeJobsTitle(String jobsTitle) {
// if (this.jobsTitle != null) {
// this.jobsTitle.remove(jobsTitle);
// }
// }

// public enum UpdateType {
// PROGRESS_STAGE,
// COMPANY_NAME,
// JOB_TITLE,
// CREATED_DATE,
// LAST_MODIFIED_DATE
// }

// public void updateFrom(Map<String, Object> fields) {
// if (fields != null) {
// // other fields...
// if (fields.containsKey("userName")) {
// this.userName = (String) fields.get("userName");
// }

// if (fields.containsKey("jobsTitle")) {
// Object jobsTitleObj = fields.get("jobsTitle");
// if (jobsTitleObj instanceof List<?>) {
// List<?> rawList = (List<?>) jobsTitleObj;
// if (rawList.isEmpty() || rawList.get(0) instanceof String) {
// List<String> stringList = rawList.stream()
// .map(Object::toString)
// .collect(Collectors.toList());
// this.jobsTitle = stringList;
// } else {
// throw new IllegalArgumentException("Not all elements in jobsTitle are of type
// String");

// }
// } else {
// throw new IllegalArgumentException("jobsTitle field is not of type List");
// }
// }
// if (fields.containsKey("progressStage")) {
// this.progressStage = (int) fields.get("progressStage");
// }

// if (fields.containsKey("companyName")) {
// Object companyTitleObj = fields.get("companyTitle");
// if (companyTitleObj instanceof List<?>) {
// List<?> rawList = (List<?>) companyTitleObj;
// if (rawList.isEmpty() || rawList.get(0) instanceof String) {
// List<? extends String> stringList = rawList.stream()
// .map(Object::toString)
// .collect(Collectors.toList());
// this.companyName = new ArrayList<>(stringList);
// } else {
// throw new IllegalArgumentException("Not all elements in companyTitle are of
// type String");

// }
// } else {
// throw new IllegalArgumentException("company field is not of type List");
// }
// }

// if (fields.containsKey("createdDate")) {
// this.createdDate = (Date) fields.get("createdDate");
// }
// if (fields.containsKey("lastModifiedDate")) {
// this.lastModifiedDate = (Date) fields.get("lastModifiedDate");
// }

// // other fields...
// }
// }

// }