// package JobHunting.repository;

// import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.stereotype.Repository;

// import java.util.Date;
// import java.util.Optional;

// import JobHunting.model.*;

// @Repository
// public interface ProgressRepository extends MongoRepository<Progress, String>
// {

// Optional<Progress> findProgressByUserName(String userName);

// Optional<Progress> findProgressByCompanyName(String companyName);

// Optional<Progress> findProgressByJobsTitle(String jobsTitle);

// Optional<Progress> findProgressByProgressStage(int progressStage);

// Optional<Progress> findProgressByCreatedDate(Date createdDate);

// Optional<Progress> findProgressByLastModifiedDate(Date lastModifiedDate);

// void deleteProgressByUserName(String userName);

// void deleteProgressByCompanyName(String companyName);

// void deleteProgressByJobsTitle(String jobsTitle);

// void deleteProgressByProgressStage(int progressStage);

// void deleteProgressByCreatedDate(Date createdDate);

// void deleteProgressByLastModifiedDate(Date lastModifiedDate);

// Progress findByUserName(String userName);

// Progress findByProgressStage(int parseInt);

// Progress findByCompanyName(String userName);

// Progress findByJobTitle(String userName);

// Progress findByCreatedDate(Date parseDate);

// Progress findByLastModifiedDate(Date parseDate);

// }
