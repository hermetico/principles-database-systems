package project2.gintonics.CollectionServices;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.Entities.Combination;
import project2.gintonics.Utils.Utils;

import java.util.List;

public class Combinations extends CollectionService {
    private static final String NAME = "combinations";

    public Combinations(ArangoDatabase db) {
        super(db, NAME);
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

    public void update(Combination combination){
        super.updateByKey(combination.getKey(), combination);
    }

    public boolean existsByGinAndTonic(String gin, String tonic){
        String combinationName = Utils.getCombinationName(gin, tonic);
        return super.existsByName(combinationName);
    }

    public boolean existsByGinAndTonicAndGarnish(String gin, String tonic, String garnish){
        String combinationName = Utils.getCombinationName(gin, tonic, garnish);
        return super.existsByName(combinationName);
    }


}
