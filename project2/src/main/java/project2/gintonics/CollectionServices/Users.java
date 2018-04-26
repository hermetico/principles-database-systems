package project2.gintonics.Services;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.DBService;
import project2.gintonics.Entities.User;

import java.util.List;

public class Users extends CollectionService {
    public static final String NAME = "users";

    public Users(ArangoDatabase db, DBService service) {
        super(db, service, NAME);
    }


    public List<User> getAll() {
        return super.getAll(User.class);
    }


    public void insert(User user){
        DocumentCreateEntity response = super.insert(user);
        user.setKey(response.getKey());
    }

    public User getByKey(String key) {
        return super.getByKey(key, User.class);
    }


}
