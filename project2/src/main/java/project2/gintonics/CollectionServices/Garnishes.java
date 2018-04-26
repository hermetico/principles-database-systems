package project2.gintonics.CollectionServices;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.Entities.Garnish;
import project2.gintonics.Utils.Utils;

import java.util.List;

public class Garnishes extends CollectionService{
    private static final String NAME = "garnishes";

    public Garnishes(ArangoDatabase db) {
        super(db, NAME);
    }

    public List<Garnish> getAll() {
        return super.getAll(Garnish.class);
    }

    public void insert(Garnish garnish){
        DocumentCreateEntity response = super.insert(garnish);
        //garnish.setKey(response.getKey());
    }

    public Garnish getByKey(String key) {
        return super.getByKey(key, Garnish.class);
    }

    public boolean existsByName(String name){
        return this.getByKey(Utils.getKeyFromName(name)) != null;
    }
}
