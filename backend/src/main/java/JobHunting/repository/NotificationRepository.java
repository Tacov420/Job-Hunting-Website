package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import JobHunting.model.Notification;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    Notification findFirstByOrderByIdDesc(); // get the largest id

    Optional<Notification> findById(int notificationId);

    Notification findByNotificationId(int notificationId);

    List<Notification> findByUserName(String userName);

    List<Notification> findBySentFalse();

    void deleteByUserName(String userName);

    void deleteBySent(boolean sent);

    void deleteById(int notificationId);
}
