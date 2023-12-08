package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import JobHunting.model.*;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    Company findFirstByOrderByIdDesc(); // get the largest id

    List<Company> findByUserId(int userId);

    Company findById(int id);

    void deleteById(int id);

    // for notification

    Optional<Company> findOptionalById(int id); // Replace Integer with your ID type

}
