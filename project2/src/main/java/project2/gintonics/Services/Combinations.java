package project2.gintonics.Services;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Combination;

import java.util.List;

public class Combinations extends CollectionService {
    private static final String NAME = "combinations";

    public Combinations(ArangoDatabase db, DBService service) {
        super(db, service, NAME);
    }

    public List<Combination> getAll() {
        return super.getAll(Combination.class);
    }

    public void insert(Combination combination){
        DocumentCreateEntity response = super.insert(combination);
        combination.setKey(response.getKey());
    }

    public Combination getByKey(String key) {
        return super.getByKey(key, Combination.class);
    }


}
