package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.*;

import JobHunting.service.*;
import JobHunting.model.*;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getNotificationsByUser(@PathVariable String userName,
            @RequestBody Notification notification) {

        try {
            List<Notification> notifications = notificationService.getNotificationByUserName(userName);

            if (notifications.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Job notifications found for user: " + userName);
            }

            // Mark each notification as read and collect IDs
            notifications.forEach(notif -> {
                notif.setRead(true); // Mark as read
            });

            // Save all notifications after updating their read status
            notificationService.saveNotification(notifications);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userName}/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable String userName,
            @PathVariable int notificationId) {
        try {
            notificationService.deleteNotification(notificationId);

            return ResponseEntity.ok("Notification deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/{userName}/{companyName}/{jobTitle}")
    public ResponseEntity<?> createNotification(@PathVariable String userName, @PathVariable String companyName,
            @PathVariable String jobTitle, @RequestBody Notification notification) {
        try {
            // Create a new Job object with the company name
            Job job = new Job();
            job.setCompany(companyName);
            job.setJobTitle(jobTitle);

            // Call a method to create and send the notification with the correct user name
            notificationService.createJobNotificationMessageAndSend(job, userName);
            return ResponseEntity.ok(notification);
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
