package project2.gintonics.Services;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import project2.gintonics.DBService;
import project2.gintonics.Entities.User;

import java.util.List;

public class Users extends CollectionService {
    private final String NAME = "users";

    public Users(ArangoDatabase db, DBService service) {
        super(db, service);
        collection = collection(NAME);
    }

    public void resetCollection(){
        resetCollection(NAME);
    }

    public List<BaseDocument> getAll() {
        return super.getAll(NAME);
    }

    public int getSize(){return super.getSize(NAME);}

    public void insert(User user){
        String userId  = String.valueOf(getSize(NAME) + 1);
        user.setKey(userId);
        super.insert(user.getDocument());
    }

    public User getByKey(String key) {
        BaseDocument doc = collection.getDocument(key, BaseDocument.class);
        if (doc == null ){
            return null;
        }
        return new User(doc);
    }


}
