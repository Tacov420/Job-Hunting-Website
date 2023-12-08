package JobHunting.model;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "subscription")
public class Subscription {
    @Id
    private ObjectId id; // Use ObjectId for MongoDB identifiers
    private String userName;
    private List<String> company; // Stores companies instead of job titles.
    private List<String> subscribedCompanies;

    public Subscription(String userName, Set<String> subscribedCompanies) {
        this.userName = userName;
        this.subscribedCompanies = List.copyOf(subscribedCompanies);
    }

}
