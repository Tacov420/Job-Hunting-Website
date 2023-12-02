package JobHunting.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByCategoryId(int categoryId);
    Post findFirstByOrderByIdDesc();                                             // get the largest id
    Post findById(int id);
    void deleteById(int id);
}
