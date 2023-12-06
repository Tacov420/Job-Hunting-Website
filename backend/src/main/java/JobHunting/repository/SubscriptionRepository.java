package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import JobHunting.model.Subscription;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    Optional<Subscription> findByUserName(String userName);

    List<Subscription> findAll();

    void deleteBySubscriptionId(int subscriptionId);

    void deleteByUserName(String userName);

}
