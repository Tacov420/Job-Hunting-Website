package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

import JobHunting.model.*;

@Repository
public interface ProgressRepository extends MongoRepository<Progress, String> {

    // Notification
    @Query("{ $or: [ { 'dateFirstInterview': { $gte: ?0, $lte: ?1 } }, { 'dateSecondInterview': { $gte: ?0, $lte: ?1 } } ] }")
    List<Progress> findProgressWithInterviewsInNextDays(Date currentDate, Date thresholdDate);

    Progress findFirstByOrderByProgressIdDesc();

    Progress findProgressByProgressId(int progressId);

    List<Progress> findProgressByUserName(String userName);

    List<Progress> findByUserNameAndProgressId(String userName, int progressId);

    void deleteProgressByUserNameAndProgressId(String userName, int progressId);

    void deleteProgressByUserName(String userName);

    void deleteProgressByProgressId(int progressId);

    void deleteProgressByCompanyName(String companyName);

    void deleteProgressByJobsTitle(String jobsTitle);

    void deleteProgressByProgressStage(int progressStage);

    void deleteProgressByCreatedDate(Date createdDate);

    void deleteProgressByLastModifiedDate(Date lastModifiedDate);

}
