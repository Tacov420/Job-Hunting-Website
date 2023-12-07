package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JobHunting.model.Progress;
import JobHunting.model.ApiResponse;
import JobHunting.service.ProgressService;
import JobHunting.service.ProgressService.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("/{userName}")
    public ResponseEntity<ApiResponse> createProgress(@PathVariable String userName,
            @Valid @RequestBody Progress progress) {
        try {
            Progress createdProgress = progressService.createProgress(progress);
            ApiResponse response = new ApiResponse("Progress has been created successfully.", createdProgress);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            ApiResponse response = new ApiResponse("Failed to create progress due to data integrity violation.", null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace to the console or a log file
            ApiResponse response = new ApiResponse("An unexpected error occurred: " + e.toString(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userName}/{progressId}")
    public ResponseEntity<ApiResponse> addProgressStage(@PathVariable String userName,
            @PathVariable int progressId,
            @Valid @RequestBody Progress progressDetails) {
        try {
            Progress updatedProgress = progressService.addProgressStage(userName, progressId,
                    progressDetails.getProgressStage());
            ApiResponse response = new ApiResponse("Progress stage added successfully", updatedProgress);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProgressNotFoundException e) {
            ApiResponse response = new ApiResponse(e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("An unexpected error occurred: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ
    @GetMapping("/{userName}")
    public ResponseEntity<ApiResponse> getProgressByUserName(@PathVariable String userName) {
        try {
            List<Progress> progressList = progressService.getProgressByUserName(userName);
            ApiResponse response = new ApiResponse("Progress successfully retrieved.", progressList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProgressNotFoundException e) {
            ApiResponse response = new ApiResponse("No progress found for userName: " + userName, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("An unexpected error occurred.", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("{userName}/{progressId}")
    public ResponseEntity<ApiResponse> updateProgress(@PathVariable String userName, @PathVariable int progressId,
            @Valid @RequestBody Progress progress) {
        try {
            List<Progress> updatedProgressList = progressService.updateProgress(userName, progressId, progress);
            ApiResponse response = new ApiResponse("Progress successfully updated.", updatedProgressList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProgressNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(
                    new ApiResponse("Failed to update progress due to data integrity violation.", null),
                    HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("An unexpected error occurred: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE using ID
    @DeleteMapping("{userName}/{progressId}")
    public ResponseEntity<ApiResponse> deleteProgressByProgressId(
            @PathVariable String userName, @PathVariable int progressId) {
        try {
            progressService.deleteProgress(userName, progressId);
            ApiResponse response = new ApiResponse("Progress successfully deleted.", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProgressNotFoundException e) {
            ApiResponse response = new ApiResponse("Progress not found for ID: " + progressId, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("An unexpected error occurred.", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(ProgressNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProgressNotFoundException(ProgressNotFoundException e) {
        ApiResponse response = new ApiResponse(e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ApiResponse response = new ApiResponse("Data integrity violation.", null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception e) {
        ApiResponse response = new ApiResponse("An unexpected error occurred: " + e.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
