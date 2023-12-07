package JobHunting.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "post")
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class Post {
    @Id
    private ObjectId _id;
    private int id;
    private int userId;
    private int categoryId;
    private String title;
    private String content;
    private LocalDateTime timestamp;

    public void setPost(int userId, int categoryId, String postTitle, String postContent, int id, LocalDateTime time) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.title = postTitle;
        this.content = postContent;
        this.timestamp = time;
    }
}
