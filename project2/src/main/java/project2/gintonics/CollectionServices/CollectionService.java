package project2.gintonics.CollectionServices;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.util.MapBuilder;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class CollectionService implements ICollectionService {
    private String _CNAME;
    protected ArangoDatabase db;
    protected ArangoCollection collection;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    public CollectionService(ArangoDatabase db, String name){
        this.db = db;
        this._CNAME = name;
        collection = loadCollection(_CNAME);
    }


    private ArangoCollection loadCollection(String collectionName){
        try {
            collection = db.collection(collectionName);
            collection.load();
        }catch(ArangoDBException e){
            //If collection does not exist, fails silently
            err.println("Warning, collection "+ collectionName +" did not exist, created.");
            createAndLoadCollection(collectionName);

        }
        return collection;
    }

    private void createAndLoadCollection(String collectionName) {

        try {
            db.createCollection(collectionName);
        } catch (ArangoDBException e) {
            err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
        }
        collection = loadCollection(collectionName);
    }

    private void removeCollection(String collectionName){
        try {
            db.collection(collectionName).drop();
        } catch (ArangoDBException e) {
            err.println("Collection " + collectionName + "did not exist");
        }
        collection = null;
    }

    public void resetCollection(){
        removeCollection(_CNAME);
        createAndLoadCollection(_CNAME);
    }

    @Override
    public <T> List<T> getAll(Class<T> asType) {
        String query = "FOR t IN " + _CNAME + " RETURN t";
        return db.query(query, null, null, asType).asListRemaining();
    }

    @Override
    public int getSize() {
        String query = "FOR t IN " + _CNAME + " COLLECT WITH COUNT INTO number RETURN {total:number}";
        String size = db.query(query, null, null, BaseDocument.class)
                .asListRemaining()
                .get(0)
                .getAttribute("total")
                .toString();
        return Integer.parseInt(size);
    }

    @Override
    public boolean exists(String key) {
        return collection.getDocument(key, BaseDocument.class) != null;
    }

    @Override
    public boolean existsByName(String name) {
        String query = "FOR t IN " + _CNAME+ " FILTER t.name == @name return t";
        Map<String, Object> bindVars = new MapBuilder()
                .put("name", name)
                .get();
        return db.query(query, bindVars, null, BaseDocument.class).asListRemaining().size() != 0;
    }

    @Override
    public void delete(String key) {
        collection.deleteDocument(key);
    }

    @Override
    public void deleteByKey(String key) {
        collection.deleteDocument(key);
    }

    @Override
    public <T> T getByKey(String key,  Class<T> asType) {
        return collection.getDocument(key, asType);
    }

    @Override
    public <T> DocumentCreateEntity insert(T obj) {
        return collection.insertDocument(obj);
    }

    @Override
    public String getCollectionName() {
        return _CNAME;
    }

    protected <T> void updateByKey(String key, T obj) {
        collection.updateDocument(key, obj);
    }

}
