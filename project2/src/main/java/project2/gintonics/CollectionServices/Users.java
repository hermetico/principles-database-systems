package project2.gintonics.CollectionServices;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.Entities.User;

import java.util.List;

public class Users extends CollectionService {
    public static final String NAME = "users";

    public Users(ArangoDatabase db) {
        super(db, NAME);
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

    public void update(User user){
        super.updateByKey(user.getKey(), user);
    }


    public List<User> getByName(String name){
        return super.getByName(name, User.class);
    }

}
