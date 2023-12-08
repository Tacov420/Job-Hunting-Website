package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

// import javax.validation.Valid;

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
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable String userName) {
        try {
            List<Notification> notifications = notificationService.getNotificationsByUserName(userName);

            // Iterate over notifications and mark each as read
            notifications.forEach(notification -> {
                notificationService.markAsRead(notification.getId()); // Assuming getId() exists in Notification
            });

            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{userName}/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable ObjectId notificationId) {
        try {
            notificationService.deleteNotification(notificationId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
