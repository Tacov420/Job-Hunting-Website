package JobHunting.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findAll();
}
