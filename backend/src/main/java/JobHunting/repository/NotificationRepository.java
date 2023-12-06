package JobHunting.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import JobHunting.model.Notification;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    Optional<Notification> findById(ObjectId id);

    List<Notification> findByUserName(String userName);

    List<Notification> findAll();

    List<Notification> findBySentFalse();

    void getByUserName(String userName);

    void deleteByUserName(String userName); // Change return type to void if not returning the deleted entity

    void deleteBySent(boolean sent); // Change return type to void if not returning the deleted entity

}
