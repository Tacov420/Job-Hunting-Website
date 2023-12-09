package JobHunting.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    Notification findFirstByOrderByNotificationIdDesc();                                             // get the largest id
    List<Notification> findByUserId(int userId);
    List<Notification> findByUserIdAndIsRead(int userId, boolean isRead);
    Notification findByNotificationId(int notificationId);
    void deleteByNotificationId(int notificationId);
}

