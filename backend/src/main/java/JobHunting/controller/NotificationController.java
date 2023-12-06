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

import java.util.*;

import JobHunting.service.*;
import JobHunting.model.*;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/all/{userName}")
    public ResponseEntity<Object> getNotification(@PathVariable String userName) {
        try {
            List<Notification> notifications = notificationService.getAllNotification(userName);
            if (notifications.isEmpty()) {
                return new ResponseEntity<>("No notifications found for the user", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete/{userName}/{notificationId}")
    public ResponseEntity<Object> deleteNotification(@PathVariable String userName, @PathVariable int notificationId) {
        try {
            boolean isDeleted = notificationService.deleteNotification(userName, notificationId);
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

}
