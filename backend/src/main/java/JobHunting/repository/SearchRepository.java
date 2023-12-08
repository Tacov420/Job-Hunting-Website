package JobHunting.repository;

import JobHunting.model.*;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends MongoRepository<Search, String> {
  List<Search> findByCompany(String company);

  List<Search> findByJobTitle(String jobTitle);

  List<Search> findByLevel(String level);
}

