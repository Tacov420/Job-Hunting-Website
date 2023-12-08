package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import javax.validation.Valid;

import JobHunting.service.*;
import JobHunting.model.*;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/all/{userName}")
    public ResponseEntity<Object> getNotification(@PathVariable String userName) {
        try {

            List<Notification> notifications = notificationService.getAllNotificationsForUser(userName);
            if (notifications.isEmpty()) {
                return new ResponseEntity<>("No notifications found for the user", HttpStatus.NOT_FOUND);
            }
            boolean isUpdated = notificationService.markAsRead(userName);
            if (!isUpdated) {
                return new ResponseEntity<>("Notifications not found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userName}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userName) {
        List<Notification> userNotifications = notificationService.getAllNotificationsForUser(userName);
        if (userNotifications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userNotifications);
    }

    @DeleteMapping(value = "/{userName}/{notificationId}")
    public ResponseEntity<Object> deleteNotification(@PathVariable String userName,
            @PathVariable String notificationIdAsString) {
        try {
            boolean isDeleted = notificationService.deleteNotification(userName, notificationIdAsString);
            if (isDeleted) {
                return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Notification not found or not belonging to the user",
                        HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{userName}")
    public ResponseEntity<Object> sendJobNotifications(@PathVariable String userName,
            @RequestBody JobSubscriptionRequest request) {
        try {
            notificationService.notifySubscribedJobs(request.getUserName());
            return new ResponseEntity<>("Notifications sent successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // For subscription
    @PostMapping(value = "/subscribe/{userName}")
    public ResponseEntity<Object> subscribeToJob(@PathVariable String userName,
            @Valid @RequestBody JobSubscriptionRequest request) {
        try {
            notificationService.subscribeToJob(request.getUserName(), request.getCompany());
            return new ResponseEntity<>("Subscribed successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // For unsubscribing from notifications for a company's jobs
    @PostMapping(value = "/unsubscribe/{userName}")
    public ResponseEntity<Object> unsubscribeFromJob(@PathVariable String userName,
            @RequestBody JobSubscriptionRequest request) {
        try {
            notificationService.unsubscribeFromJob(request.getUserName(), request.getCompany());
            return new ResponseEntity<>("Unsubscribed from job notifications successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Inner class to handle subscription requests
    static class JobSubscriptionRequest {
        private String userName;
        private String company; // Changed from jobTitle to company

        // Getter and setter for userName
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        // Getter and setter for company
        public String getCompany() {

            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }

}
