package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.Profile;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

    // Personal Info
    Profile findProfileByUserName(String userName); // get userName

    Profile findFirstByOrderByIdDesc(); // get the largest id

    Profile findProfileByEmail(String email);

    Profile findProfileById(String _id);

    // Register
    Profile findProfileByRegisterStage(int registerStage);

    Profile findProfileByVerificationCode(String verificationCode);

    Profile findByUserName(String userName); // get userName

    Profile findByEmail(String email);

    // Preference

    Profile findProfileByDesiredJobsTitle(String[] desiredJobsTitle);

    Profile findProfileByDesiredJobsLocation(String[] desiredJobsLocation);

    Profile findProfileBySkills(String[] skills);

    Profile findProfileByCompanies(String[] companies);

    Profile findPreferenceByUserName(String userName);

}
