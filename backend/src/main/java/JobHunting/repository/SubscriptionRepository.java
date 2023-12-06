package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import JobHunting.model.Subscription;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findAll();

    Subscription findFirstByOrderByIdDesc(); // get the largest id

    Subscription findByUserName(String userName);

    Subscription findBySubscriptionId(int subscriptionId);

    Subscription findForUser();

    Subscription deleteBySubscriptionId(int subscriptionId);

    Subscription deleteByUserName(String userName);

}
