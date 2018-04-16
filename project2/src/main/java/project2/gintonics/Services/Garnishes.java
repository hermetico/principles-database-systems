package project2.gintonics.Services;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import project2.gintonics.Entities.Garnish;

import java.util.List;

public class Garnishes extends CollectionService{
    private final String NAME = "garnishes";
    private ArangoCollection collection;

    public Garnishes(ArangoDatabase db) {
        super(db);
        collection = collection(NAME);
    }

    public void resetCollection(){
        resetCollection(NAME);
    }

    protected List<BaseDocument> getAll() {
        return super.getAll(NAME);
    }

    public void insert(Garnish garnish) {
        super.insert(garnish.getDocument());
    }


    public Garnish getbyKey(String name) {
        BaseDocument doc = collection.getDocument(name, BaseDocument.class);
        if (doc == null ){
            return null;
        }
        return new Garnish(doc);
    }


}
