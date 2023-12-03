package JobHunting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.Company;
import JobHunting.model.Profile;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    Profile findFirstByOrderByIdDesc(); // get the largest id

}
