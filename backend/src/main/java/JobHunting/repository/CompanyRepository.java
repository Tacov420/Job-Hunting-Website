package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    Company findFirstByOrderByIdDesc();                                             // get the largest id
}
