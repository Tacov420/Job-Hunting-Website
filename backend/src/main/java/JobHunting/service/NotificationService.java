package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import JobHunting.repository.*;
import JobHunting.model.*;

@Service
public class NotificationService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ProgressRepository progressRepository;

    public int getUserId(String userName) {
        Profile profile = profileRepository.findByUserName(userName);
        if (profile == null) {
            return -1;
        }
        return profile.getId();
    }

    public Object getNotificationList(int userId) {
        List<Notification> notificationList = notificationRepository.findByUserId(userId);
        Map<String, List<Object>> returnMap = new HashMap<>();
        for (Notification notification: notificationList) {
            List<Object> notificationData = new ArrayList<>();
            notificationData.add(notification.getContent());
            notificationData.add(notification.getIsRead());
            returnMap.put(String.valueOf(notification.getNotificationId()), notificationData);
        }

        return returnMap;
    }

    public String readAllNotifications(int userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsRead(userId, false);
        for (Notification notification: unreadNotifications) {
            notification.readNotification();
            notificationRepository.save(notification);
        }

        return "All notifications were read";
    }

    public boolean checkNotificationId(int notificationId) {
        Notification notification = notificationRepository.findByNotificationId(notificationId);
        if (notification == null) {
            return false;
        }
        return true;
    }

    public String createNotification(int userId, String content) {
        int id;
        Notification largestNotification = notificationRepository.findFirstByOrderByNotificationIdDesc();
        if (largestNotification != null){
            id = largestNotification.getNotificationId() + 1;
        } else {
            id = 0;
        }
        Notification notification = new Notification();
        notification.setNotification(id, userId, content);
        notificationRepository.save(notification);

        return "Notification was created successfully";
    }

    public boolean checkPermission(int userId, int notificationId) {
        Notification notification = notificationRepository.findByNotificationId(notificationId);
        if (notification.getUserId() != userId) {
            return false;
        }
        return true;
    }

    public String deleteNotification(int notificationId) {
        notificationRepository.deleteByNotificationId(notificationId);

        return "Delete notification successfully";
    }


    public String addProgressNotification() {
        List<Progress> progressList = progressRepository.findAll();
        LocalDate threeDaysLater = LocalDate.now().plusDays(3);
        LocalDate oneDayLater = LocalDate.now().plusDays(1);
        for(Progress progress: progressList) {
            LocalDate date = progress.getDates().getLast();
            if(date.isEqual(threeDaysLater)) {
                createNotification(progress.getUserId(), String.format("%s of %s %s is in 3 days!", progress.getStages().getLast(), progress.getCompanyName(), progress.getJobTitle()));
            }
            else if(date.isEqual(oneDayLater)) {
                createNotification(progress.getUserId(), String.format("%s of %s %s is on tomorrow!", progress.getStages().getLast(), progress.getCompanyName(), progress.getJobTitle()));
            }
        }

        return "Notifications of upcoming stages was created successfully";
    }
}

