package project2.gintonics.Services;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Garnish;

import java.util.List;

public class Garnishes extends CollectionService{
    private static final String NAME = "garnishes";

    public Garnishes(ArangoDatabase db, DBService service) {
        super(db, service, NAME);
    }

    public List<Garnish> getAll() {
        return super.getAll(Garnish.class);
    }

    public void insert(Garnish garnish){
        DocumentCreateEntity response = super.insert(garnish);
        //garnish.setKey(response.getKey());
    }

    public Garnish getbyKey(String key) {
        return super.getByKey(key, Garnish.class);
    }


}
