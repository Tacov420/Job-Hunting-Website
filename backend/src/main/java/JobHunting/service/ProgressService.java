// package JobHunting.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// import JobHunting.model.Progress;
// import JobHunting.repository.ProgressRepository;

// // import java.text.ParseException;
// // import java.text.SimpleDateFormat;
// import java.util.Date;

// @Service
// public class ProgressService {

// @Autowired
// private final ProgressRepository progressRepository;

// public ProgressService(ProgressRepository progressRepository) {
// this.progressRepository = progressRepository;
// }

// public Progress createProgress(Progress progress) {
// validateProgress(progress);
// return progressRepository.save(progress);
// }

// public Progress getProgressByUserName(String userName, UpdateType updateType)
// {
// return progressRepository.findProgressByUserName(userName)
// .orElseThrow(() -> new ProgressNotFoundException("Progress not found for
// userName: " + userName));
// }

// public Progress getProgressByCompanyName(String companyName, UpdateType
// updateType) {
// return progressRepository.findProgressByCompanyName(companyName)
// .orElseThrow(() -> new ProgressNotFoundException("Progress not found for
// companyName: " + companyName));
// }

// public Progress getProgressByJobsTitle(String jobsTitle, UpdateType
// updateType) {
// return progressRepository.findProgressByJobsTitle(jobsTitle)
// .orElseThrow(() -> new ProgressNotFoundException("Progress not found for
// jobsTitle: " + jobsTitle));
// }

// public Progress getProgressByProgressStage(int progressStage, UpdateType
// updateType) {
// return progressRepository.findProgressByProgressStage(progressStage)
// .orElseThrow(
// () -> new ProgressNotFoundException("Progress not found for progressStage: "
// + progressStage));
// }

// public Progress getProgressByCreatedDate(Date createdDate, UpdateType
// updateType) {
// return progressRepository.findProgressByCreatedDate(createdDate)
// .orElseThrow(() -> new ProgressNotFoundException("Progress not found for
// createdDate: " + createdDate));
// }

// public Progress getProgressByLastModifiedDate(Date lastModifiedDate,
// UpdateType updateType) {
// return progressRepository.findProgressByLastModifiedDate(lastModifiedDate)
// .orElseThrow(() -> new ProgressNotFoundException(
// "Progress not found for lastModifiedDate: " + lastModifiedDate));
// }

// @Transactional
// public Progress updateProgress(String userName, Progress updatedProgress) {
// Progress existingProgress =
// progressRepository.findProgressByUserName(userName)
// .orElseThrow(() -> new ProgressNotFoundException("Progress not found for
// userName: " + userName));

// existingProgress.setCompanyName(updatedProgress.getCompanyName());
// existingProgress.setJobsTitle(updatedProgress.getJobsTitle());
// existingProgress.setProgressStage(updatedProgress.getProgressStage());

// return progressRepository.save(existingProgress);
// }

// // Make sure to validate the updated fields
// public void validateProgress(Progress progress) {
// if (progress.getUserName() == null || progress.getUserName().isEmpty()) {
// throw new IllegalArgumentException("Username must not be empty");
// }
// if (progress.getCompanyName() == null || progress.getCompanyName().isEmpty())
// {
// throw new IllegalArgumentException("Company name must not be empty");
// }
// if (progress.getJobsTitle() == null || progress.getJobsTitle().isEmpty()) {
// throw new IllegalArgumentException("Job title must not be empty");
// }
// if (progress.getProgressStage() < 0 || progress.getProgressStage() > 4) {
// throw new IllegalArgumentException("Progress stage must be between 0 and 4");
// }
// }

// public Progress deleteProgress(String userName, Progress progress) {
// Progress existingProgress =
// progressRepository.findProgressByUserName(userName)
// .orElseThrow(() -> new ProgressNotFoundException("Progress not found for
// userName: " + userName));
// progressRepository.delete(existingProgress);
// return existingProgress;
// }

// // private Date parseDate(String dateString) {
// // try {
// // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
// // return dateFormat.parse(dateString);
// // } catch (ParseException e) {
// // throw new IllegalArgumentException("Invalid date format: " + dateString,
// e);
// // }
// // }

// public enum UpdateType {
// COMPANY_NAME, JOB_TITLE, LAST_MODIFIED_DATE, PROGRESS_STAGE, CREATED_DATE
// }

// // Exception to throw when Progress is not found
// public class ProgressNotFoundException extends RuntimeException {
// public ProgressNotFoundException(String message) {
// super(message);
// }
// }

// public class ProgressCreateException extends RuntimeException {
// public ProgressCreateException(String message) {
// super(message);
// }
// }

// public class ProgressReadException extends RuntimeException {
// public ProgressReadException(String message) {
// super(message);
// }
// }

// public class ProgressUpdateException extends RuntimeException {
// public ProgressUpdateException(String message) {
// super(message);
// }
// }

// public class ProgressDeleteException extends RuntimeException {
// public ProgressDeleteException(String message) {
// super(message);
// }
// }

// // Exception handler for ProgressNotFoundException

// @RestControllerAdvice
// public class GlobalExceptionHandler {

// @ExceptionHandler(ProgressNotFoundException.class)
// public ResponseEntity<String>
// handleProgressNotFoundException(ProgressNotFoundException ex) {
// // Return a response entity with the NOT_FOUND status code
// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
// }

// @ExceptionHandler(ProgressUpdateException.class)
// public ResponseEntity<String>
// handleProgressUpdateException(ProgressUpdateException ex) {
// // Return a response entity with the BAD_REQUEST status code
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
// }

// @ExceptionHandler(ProgressDeleteException.class)
// public ResponseEntity<String>
// handleProgressDeleteException(ProgressDeleteException ex) {
// // Return a response entity with the BAD_REQUEST status code
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
// }

// @ExceptionHandler(IllegalArgumentException.class)
// public ResponseEntity<String>
// handleIllegalArgumentException(IllegalArgumentException ex) {
// // Return a response entity with the BAD_REQUEST status code
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
// }

// @ExceptionHandler(Exception.class)
// public ResponseEntity<String> handleException(Exception ex) {
// // Return a response entity with the INTERNAL_SERVER_ERROR status code
// return
// ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
// }

// @ExceptionHandler(ProgressCreateException.class)
// public ResponseEntity<String>
// handleProgressCreateException(ProgressCreateException ex) {
// // Return a response entity with the BAD_REQUEST status code
// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
// }

// }

// }
