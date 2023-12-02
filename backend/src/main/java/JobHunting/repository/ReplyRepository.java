package JobHunting.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import JobHunting.model.*;

@Repository
public interface ReplyRepository extends MongoRepository<Reply, String> {
    List<Reply> findByPostId(int postId);
    Reply findFirstByOrderByIdDesc();                                             // get the largest id
    Reply findById(int id);
    void deleteById(int id);
}
