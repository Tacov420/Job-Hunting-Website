package JobHunting.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface ProgressRepository extends MongoRepository<Progress, String> {
    Progress findFirstByOrderByProgressIdDesc();

    List<Progress> findByUserId(int userId);

    Progress findByProgressId(int progressId);

    void deleteByProgressId(int progressId);
}
