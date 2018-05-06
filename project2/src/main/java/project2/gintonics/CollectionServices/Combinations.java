package project2.gintonics.CollectionServices;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.model.HashIndexOptions;
import project2.gintonics.Entities.Combination;
import project2.gintonics.Utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Combinations extends CollectionService {
    private static final String NAME = "combinations";

    public Combinations(ArangoDatabase db, boolean reset) {
        super(db, NAME);
        if(reset) resetCollection();
        createIndex("gin");
        createIndex("tonic");
        createCombinedIndex("gin", "tonic");
    }

    private void createIndex(String key){
        Collection<String> indexes = new ArrayList<String>();
        indexes.add(key);
        HashIndexOptions options = new HashIndexOptions();
        options.unique(false);
        options.sparse(false);
        collection.createHashIndex(indexes, options);
    }

    private void createCombinedIndex(String one, String two){
        Collection<String> indexes = new ArrayList<String>();
        indexes.add(one);
        indexes.add(two);
        HashIndexOptions options = new HashIndexOptions();
        options.unique(false);
        options.sparse(false);
        collection.createHashIndex(indexes, options);
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
