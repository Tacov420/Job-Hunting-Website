package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.Profile;
import java.util.List;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

    // Personal Info
    Profile findProfileByUserName(String userName); // get userName

    Profile findFirstByOrderByIdDesc(); // get the largest id

    Profile findProfileByEmail(String email);

    Profile findProfileById(String _id);

    Profile findByUserName(String userName);

    // Register
    Profile findProfileByRegisterStage(int registerStage);

    Profile findProfileByVerificationCode(String verificationCode);

    // Preference

    Profile findProfileByDesiredJobsTitle(String[] desiredJobsTitle);

    Profile findProfileByDesiredJobsLocation(String[] desiredJobsLocation);

    Profile findProfileBySkills(String[] skills);

    Profile findPreferenceByUserName(String userName);

    // for notification
    List<Profile> findByCompanyId(int companyId);

}
