package JobHunting.repository;

// import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// import java.util.Optional;

import JobHunting.model.*;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    Profile findProfileByUserName(String userName);                                 // get userName
    Profile findFirstByOrderByIdDesc();                                             // get the largest id
    Profile findProfileByEmail(String email);
}
