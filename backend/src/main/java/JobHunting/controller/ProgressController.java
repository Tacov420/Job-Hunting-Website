package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// import java.text.SimpleDateFormat;
import java.time.LocalDate;

import JobHunting.service.*;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {
    @Autowired
    private ProgressService progressService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<Object> getProgressList(@PathVariable String username) {
        try {
            int userId = progressService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("Username doesn't exist", HttpStatus.BAD_REQUEST);
            }
            Object res = progressService.getProgressList(userId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{username}/{progressId}")
    public ResponseEntity<Object> getSpecificProgress(@PathVariable String username, @PathVariable int progressId) {
        if (!progressService.checkProgressId(progressId)) {
            return new ResponseEntity<>("ProgressId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = progressService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("This user doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!progressService.checkPermission(userId, progressId)) {
                return new ResponseEntity<>("The user has no permission for this progress", HttpStatus.FORBIDDEN);
            }
            Object res = progressService.getSpecificProgress(userId, progressId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{username}/add")
    public ResponseEntity<Object> addProgress(@PathVariable String username, @RequestBody Map<String, String> body) {
        String companyName = body.get("companyName");
        String jobTitle = body.get("jobTitle");
        String stage = body.get("stage");
        LocalDate date = LocalDate.parse(body.get("date"));
        int status = Integer.parseInt(body.get("status"));
        try {
            int userId = progressService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            // if (!progressService.checkPermission(userId, progressId)) {
            //     return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            // }
            int res = progressService.createProgress(userId, companyName, jobTitle, stage, date, status);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{username}/{progressId}/add")
    public ResponseEntity<Object> addStage(@PathVariable String username, @PathVariable int progressId, @RequestBody Map<String, String> body) {
        String stageName = body.get("stageName");
        LocalDate date = LocalDate.parse(body.get("date"));
        int status = Integer.parseInt(body.get("status"));
        try {
            int userId = progressService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!progressService.checkPermission(userId, progressId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            String res = progressService.createStage(progressId, stageName, date, status);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{username}/{progressId}/edit")
    public ResponseEntity<Object> editStage(@PathVariable String username, @PathVariable int progressId, @RequestBody Map<String, String> body) {
        int index = Integer.parseInt(body.get("index"));
        String stageName = body.get("stageName");
        LocalDate date = LocalDate.parse(body.get("date"));
        int status = Integer.parseInt(body.get("status"));
        try {
            int userId = progressService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!progressService.checkPermission(userId, progressId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            String res = progressService.editStage(progressId, index, stageName, date, status);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{username}/{progressId}")
    public ResponseEntity<Object> deleteProgress(@PathVariable String username, @PathVariable int progressId) {
        // String username = body.get("username");
        if (!progressService.checkProgressId(progressId)) {
            return new ResponseEntity<>("ProgressId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = progressService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!progressService.checkPermission(userId, progressId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            String res = progressService.deleteProgress(progressId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
