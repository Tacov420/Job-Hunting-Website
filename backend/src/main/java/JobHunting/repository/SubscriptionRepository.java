package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import JobHunting.model.Subscription;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    List<Subscription> findAllByUserName(String userName);

    Optional<Subscription> findByUserName(String userName);

    List<Subscription> findAll();

    void deleteByUserName(String userName);

    List<Subscription> findByCompanyContains(String company);

}
