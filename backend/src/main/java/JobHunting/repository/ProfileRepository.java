package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    Profile findByUserName(String userName);                                 // get userName
    Profile findFirstByOrderByIdDesc();                                             // get the largest id
    Profile findByEmail(String email);
}
