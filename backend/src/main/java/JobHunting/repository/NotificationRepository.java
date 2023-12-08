package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import JobHunting.model.Notification;
import org.bson.types.ObjectId; // Import ObjectId

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {

    Optional<Notification> findById(ObjectId notificationId);

    List<Notification> findByUserName(String userName);

    List<Notification> findBySentFalse();

    void deleteByUserName(String userName);

    void deleteBySent(boolean sent);
}
