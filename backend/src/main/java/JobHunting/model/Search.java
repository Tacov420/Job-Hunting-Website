package JobHunting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {
  @Id private ObjectId _id;
  private String company;
  private String jobTitle;
  private String level;

  public void setJob(String company, String jobTitle, String level) {
    this.company = company;
    this.jobTitle = jobTitle;
    this.level = level;
  }
}
