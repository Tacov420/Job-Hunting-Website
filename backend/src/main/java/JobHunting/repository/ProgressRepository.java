package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import JobHunting.model.*;

@Repository
public interface ProgressRepository extends MongoRepository<Progress, String> {

    List<Progress> findByJobsTitle(String jobsTitle);

    List<Progress> findProgressByUserName(String userName);

    List<Progress> findProgressByCompanyName(String companyName);

    List<Progress> findProgressByJobsTitle(String jobsTitle);

    List<Progress> findProgressByProgressStage(int progressStage);

    List<Progress> findProgressByCreatedDate(Date createdDate);

    List<Progress> findProgressByLastModifiedDate(Date lastModifiedDate);

    void deleteProgressByUserName(String userName);

    void deleteProgressByCompanyName(String companyName);

    void deleteProgressByJobsTitle(String jobsTitle);

    void deleteProgressByProgressStage(int progressStage);

    void deleteProgressByCreatedDate(Date createdDate);

    void deleteProgressByLastModifiedDate(Date lastModifiedDate);

}
