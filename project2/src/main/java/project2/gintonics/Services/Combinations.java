package project2.gintonics.Services;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Combination;

import java.util.List;

public class Combinations extends CollectionService {
    private final String NAME = "combinations";

    public Combinations(ArangoDatabase db, DBService service) {
        super(db, service);
        collection = collection(NAME);
    }

    public void resetCollection(){
        resetCollection(NAME);
    }

    public List<BaseDocument> getAll() {
        return super.getAll(NAME);
    }

    public void insert(Combination combination){
        super.insert(combination.getDocument());
    }

    public Combination getByKey(String key) {
        BaseDocument doc = collection.getDocument(key, BaseDocument.class);
        if (doc == null ){
            return null;
        }
        return new Combination(doc);
    }

    public void UpdateRatings(){

    }


}
