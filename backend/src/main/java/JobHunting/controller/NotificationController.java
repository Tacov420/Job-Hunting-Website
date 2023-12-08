package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.*;

import JobHunting.service.*;
import JobHunting.model.*;

import org.bson.types.ObjectId;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getNotificationsByUser(@PathVariable String userName) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByUserName(userName);

            if (notifications.isEmpty()) {
                // User or notifications not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No notifications found for user: " + userName);
            }

            notifications.forEach(notification -> {
                notificationService.markAsRead(notification.getId()); // Mark as read
            });
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userName}/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable String userName,
            @PathVariable ObjectId notificationId) {
        try {
            notificationService.deleteNotification(notificationId);

            return ResponseEntity.ok("Notification deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        try {
            notificationService.checkAndNotifyNewJobs();

            return ResponseEntity.ok("Test successful.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error: " + e.getMessage());
        }
    }

}
