package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import JobHunting.model.Progress;
import JobHunting.repository.ProgressRepository;

import java.util.Date;
import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    // CREATE
    @Transactional
    public Progress createProgress(Progress progress) {
        Progress newProgress = new Progress();
        Progress progressId = progressRepository.findFirstByOrderByProgressIdDesc();
        if (progressId != null) {
            newProgress.setProgressId(progressId.getProgressId() + 1);
        } else {
            newProgress.setProgressId(0);
        }
        newProgress.setUserName(progress.getUserName());
        newProgress.setCompanyName(progress.getCompanyName());
        newProgress.setJobsTitle(progress.getJobsTitle());
        newProgress.setProgressStage(progress.getProgressStage());
        newProgress.setDateApplied(progress.getDateApplied());
        newProgress.setDateFirstInterview(progress.getDateFirstInterview());
        newProgress.setDateSecondInterview(progress.getDateSecondInterview());
        newProgress.setDateOffer(progress.getDateOffer());
        newProgress.setDateRejected(progress.getDateRejected());

        newProgress.setCreatedDate(new Date());

        validateProgress(newProgress);
        progressRepository.save(newProgress);

        return newProgress;

    }

    // READ by 一個一個
    public Progress getProgressById(int progressId) {
        return progressRepository.findById(String.valueOf(progressId))
                .orElseThrow(() -> new ProgressNotFoundException("Progress not found for ID: " + progressId));
    }

    // read by 全部
    public List<Progress> getProgressByUserName(String userName) {
        List<Progress> progressList = progressRepository.findProgressByUserName(userName);
        if (progressList.isEmpty()) {
            throw new ProgressNotFoundException("No progress found for userName: " + userName);
        }
        return progressList;
    }

    @Transactional
    public List<Progress> updateProgress(String userName, int progressId, Progress updatedProgress) {
        List<Progress> existingProgresses = progressRepository.findByUserNameAndProgressId(userName, progressId);
        if (existingProgresses.isEmpty()) {
            throw new ProgressNotFoundException(
                    "Progress not found for userName: " + userName + " and progressId: " + progressId);
        }

        for (Progress existingProgress : existingProgresses) {
            existingProgress.setCompanyName(updatedProgress.getCompanyName());
            existingProgress.setJobsTitle(updatedProgress.getJobsTitle());
            existingProgress.setProgressStage(updatedProgress.getProgressStage());
            existingProgress.setDateApplied(updatedProgress.getDateApplied());
            existingProgress.setDateFirstInterview(updatedProgress.getDateFirstInterview());
            existingProgress.setDateSecondInterview(updatedProgress.getDateSecondInterview());
            existingProgress.setDateOffer(updatedProgress.getDateOffer());
            existingProgress.setDateRejected(updatedProgress.getDateRejected());

            existingProgress.setLastModifiedDate(new Date());
            existingProgress.setCreatedDate(existingProgress.getCreatedDate());

            validateProgress(existingProgress);
        }

        return progressRepository.saveAll(existingProgresses);
    }

    // Make sure to validate the updated fields
    public void validateProgress(Progress progress) {

        if (progress.getCompanyName() == null || progress.getCompanyName().isEmpty()) {
            throw new IllegalArgumentException("Company name must not be empty");
        }
        if (progress.getJobsTitle() == null || progress.getJobsTitle().isEmpty()) {
            throw new IllegalArgumentException("Job title must not be empty");
        }
        if (progress.getProgressStage() < 0 || progress.getProgressStage() > 4) {
            throw new IllegalArgumentException("Progress stage must be between 0 and 4");
        }
    }

    public void deleteProgress(String userName, int progressId) {
        List<Progress> existingProgressList = progressRepository.findByUserNameAndProgressId(userName, progressId);
        progressRepository.deleteProgressByUserNameAndProgressId(userName, progressId);
        if (existingProgressList.isEmpty()) {
            throw new ProgressNotFoundException(
                    "Progress not found for userName: " + userName + " and progressId: " + progressId);
        }
        if (existingProgressList.isEmpty()) {
            throw new ProgressNotFoundException("Progress not found for userName: " + userName);
        }

        return;
    }

    public enum UpdateType {
        PROGRESS_STAGE,
        COMPANY_NAME,
        JOB_TITLE,
        CREATED_DATE,
        LAST_MODIFIED_DATE,
        DATE_FIRST_INTERVIEW,
        DATE_SECOND_INTERVIEW,
        DATE_OFFER,
        DATE_REJECTED;
    }

    // Exception to throw when Progress is not found
    public class ProgressNotFoundException extends RuntimeException {
        public ProgressNotFoundException(String message) {
            super(message);
        }
    }

    public class ProgressCreateException extends RuntimeException {
        public ProgressCreateException(String message) {
            super(message);
        }
    }

    public class ProgressReadException extends RuntimeException {
        public ProgressReadException(String message) {
            super(message);
        }
    }

    public class ProgressUpdateException extends RuntimeException {
        public ProgressUpdateException(String message) {
            super(message);
        }
    }

    public class ProgressDeleteException extends RuntimeException {
        public ProgressDeleteException(String message) {
            super(message);
        }
    }

    public class ProgressValidationException extends RuntimeException {
        public ProgressValidationException(String message) {
            super(message);
        }
    }

    // Exception handler for ProgressNotFoundException

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(ProgressNotFoundException.class)
        public ResponseEntity<String> handleProgressNotFoundException(ProgressNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        @ExceptionHandler(ProgressUpdateException.class)
        public ResponseEntity<String> handleProgressUpdateException(ProgressUpdateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(ProgressDeleteException.class)
        public ResponseEntity<String> handleProgressDeleteException(ProgressDeleteException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleException(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

        @ExceptionHandler(ProgressCreateException.class)
        public ResponseEntity<String> handleProgressCreateException(ProgressCreateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(ProgressReadException.class)
        public ResponseEntity<String> handleProgressReadException(ProgressReadException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        @ExceptionHandler(ProgressValidationException.class)
        public ResponseEntity<String> handleProgressValidationException(ProgressValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

}
