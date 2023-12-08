package JobHunting.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findAll();

    // Notification

    List<Job> findByJobTitle(String jobTitle);

    List<Job> findByCompany(String company);

    List<Job> findByNotificationSentFalse();

    List<Job> findByCompanyAndNotificationSentFalse(String company, boolean notificationSent);

    List<Job> findByCompanyAndNotificationSentFalse(String company);

    List<Job> findLatestJobsByCompany(String company);

}
