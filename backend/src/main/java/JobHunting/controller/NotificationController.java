package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import JobHunting.service.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<Object> getNotificationList(@PathVariable String username) {
        try {
            int userId = notificationService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            }
            Object res = notificationService.getNotificationList(userId);
            notificationService.readAllNotifications(userId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{username}/{notificationId}")
    public ResponseEntity<Object> deleteNotification(@PathVariable String username, @PathVariable int notificationId) {
        if (!notificationService.checkNotificationId(notificationId)) {
            return new ResponseEntity<>("NotificationId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = notificationService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!notificationService.checkPermission(userId, notificationId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            String res = notificationService.deleteNotification(notificationId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{username}/add")
    public ResponseEntity<Object> addNotification(@PathVariable String username, @RequestBody Map<String, String> body) {
        String content = body.get("content");
        try {
            int userId = notificationService.getUserId(username);
            if (userId == -1) {
                return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            }
            int res = notificationService.createNotification(userId, content);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
