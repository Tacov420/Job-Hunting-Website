package JobHunting.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
// import java.util.List;s

@Document(collection = "company")
@Data
@AllArgsConstructor 
@NoArgsConstructor


public class Company {
    @Id
    private ObjectId _id;
    private int id;
    private int userId;
    private String name;
    private int receiveEmail;


    public void setCompany(int userId, String name, int id, int receiveEmail) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.receiveEmail = receiveEmail;
    }
    
}
