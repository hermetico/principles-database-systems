package project2.gintonics.Services;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Tonic;


import java.util.List;

public class Tonics extends CollectionService{
    private final String NAME = "tonics";

    public Tonics(ArangoDatabase db, DBService service) {
        super(db, service);
        collection = collection(NAME);
    }

    public void resetCollection(){
        resetCollection(NAME);
    }

    public List<BaseDocument> getAll() {
        return super.getAll(NAME);
    }

    public void insert(Tonic tonic){
        super.insert(tonic.getDocument());
    }

    public Tonic getByKey(String key) {
        BaseDocument doc = collection.getDocument(key, BaseDocument.class);
        if (doc == null ){
            return null;
        }
        return new Tonic(doc);
    }


}
