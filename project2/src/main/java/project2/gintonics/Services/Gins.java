package project2.gintonics.Services;


import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import project2.gintonics.Entities.Gin;
import java.util.List;

public class Gins extends CollectionService{
    private final String NAME = "gins";

    public Gins(ArangoDatabase db) {
        super(db);
        collection = collection(NAME);
    }

    public void resetCollection(){
        resetCollection(NAME);
    }

    public List<BaseDocument> getAll() {
        return super.getAll(NAME);
    }


    public void insert(Gin gin) {
        super.insert(gin.getDocument());
    }

    public Gin getByKey(String key) {
        BaseDocument doc = collection.getDocument(key, BaseDocument.class);
        if (doc == null ){
            return null;
        }
        return new Gin(doc);
    }



}
