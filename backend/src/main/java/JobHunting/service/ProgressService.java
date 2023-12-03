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
    public Progress createProgress(Progress progress) {
        validateProgress(progress);
        return progressRepository.save(progress);
    }

    // READ by 一個一個
    public Progress getProgressById(String Objectid) {
        return progressRepository.findById(Objectid)
                .orElseThrow(() -> new ProgressNotFoundException("Progress not found for ID: " + Objectid));
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
    public Progress updateProgress(String Objectid, Progress updatedProgress) {
        Progress existingProgress = progressRepository.findById(Objectid)
                .orElseThrow(() -> new ProgressNotFoundException("Progress not found for ID: " + Objectid));

        existingProgress.setCompanyName(updatedProgress.getCompanyName());
        existingProgress.setJobsTitle(updatedProgress.getJobsTitle());
        existingProgress.setProgressStage(updatedProgress.getProgressStage());
        existingProgress.setLastModifiedDate(new Date());

        return progressRepository.save(existingProgress);
    }

    // Make sure to validate the updated fields
    public void validateProgress(Progress progress) {
        if (progress.getUserName() == null || progress.getUserName().isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
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

    public void deleteProgress(String userName) {
        List<Progress> existingProgressList = progressRepository.findProgressByUserName(userName);

        if (existingProgressList.isEmpty()) {
            throw new ProgressNotFoundException("Progress not found for userName: " + userName);
        }

        for (Progress progress : existingProgressList) {
            progressRepository.delete(progress);
        }
    }

    public void deleteProgressById(String id) {
        Progress existingProgress = progressRepository.findById(id)
                .orElseThrow(() -> new ProgressNotFoundException("Progress not found for ID: " + id));
        progressRepository.delete(existingProgress);
    }

    // private Date parseDate(String dateString) {
    // try {
    // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    // return dateFormat.parse(dateString);
    // } catch (ParseException e) {
    // throw new IllegalArgumentException("Invalid date format: " + dateString, e);
    // }
    // }

    public enum UpdateType {
        COMPANY_NAME, JOB_TITLE, LAST_MODIFIED_DATE, PROGRESS_STAGE, CREATED_DATE
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
