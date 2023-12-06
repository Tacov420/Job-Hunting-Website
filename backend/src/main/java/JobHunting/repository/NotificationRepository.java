package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import JobHunting.model.Notification;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findAll();

    // Notification manage for Progress and Job are put in ProgressRepository and
    // JobRepository

    List<Notification> findByNotificationSentFalse();

    Notification findFirstByOrderByNotificationIdDesc();

    Notification findByNotificationId(int notificationId);

    List<Notification> findByUserName(String userName);

    Notification deleteByNotificationId(int notificationId);

    Notification deleteByUserName(String userName);

    Notification deleteByNotificationSent(boolean notificationSent);

}
