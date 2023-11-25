package JobHunting.repository;

// import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// import java.util.Optional;

import JobHunting.model.*;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    // Personal Info
    Profile findProfileByUserName(String userName); // get userName

    Profile findFirstByOrderByIdDesc(); // get the largest id

    Profile findProfileByEmail(String email);

    Profile findProfileById(String id);

    // Personal Preference
    Profile findPreferencProfileById(String id);

    Profile findProfileByDesiredJobsTitle(String[] desiredJobsTitle);

    Profile findProfileByDesiredJobsLocation(String[] desiredJobsLocation);

    Profile findProfileBySkills(String[] skills);

    // Register
    Profile findProfileByRegisterStage(int registerStage);

    Profile findProfileByVerificationCode(String verificationCode);
}
