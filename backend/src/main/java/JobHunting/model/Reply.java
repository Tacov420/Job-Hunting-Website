package JobHunting.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "reply")
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class Reply {
    @Id
    private ObjectId _id;
    private int id;
    private int userId;
    private int postId;
    private String content;
    private LocalDateTime timestamp;

    public void setReply(int userId, int postId, String replyContent, int id, LocalDateTime time) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.content = replyContent;
        this.timestamp = time;
    }
}
