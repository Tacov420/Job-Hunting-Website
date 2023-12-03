package JobHunting.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    // Personal Info
    Profile findByUserName(String userName); // get userName

    Profile findFirstByOrderByIdDesc(); // get the largest id

    Profile findByEmail(String email);

    Optional<Profile> findById(String id);

    // Personal Preference
    Profile findPreferencProfileById(String id);

    Profile findByDesiredJobsTitle(String[] desiredJobsTitle);

    Profile findByDesiredJobsLocation(String[] desiredJobsLocation);

    Profile findBySkills(String[] skills);

    // Register
    Profile findByRegisterStage(int registerStage);

    Profile findByVerificationCode(String verificationCode);

    // Login
    ProfileDTO findDTOByUserName(String userName);

}

