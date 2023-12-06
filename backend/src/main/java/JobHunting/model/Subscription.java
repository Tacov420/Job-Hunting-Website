package JobHunting.model;

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
    private ObjectId _id;
    private String userName;
    private String endpoint; // Subscription endpoint
    private String publicKey; // Public key for subscription
    private String auth; // Auth token for subscription
    private int subscriptionId;

    public Subscription(String userName, String endpoint, String publicKey, String auth) {
        this.userName = userName;
        this.endpoint = endpoint;
        this.publicKey = publicKey;
        this.auth = auth;
    }

    public void librarySubscription(String userName, String endpoint, String publicKey, String auth) {
        this.userName = userName;
        this.endpoint = endpoint;
        this.publicKey = publicKey;
        this.auth = auth;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

}
