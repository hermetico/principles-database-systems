package project2.gintonics.Services;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import project2.gintonics.Services.Ifaces.ICommon;

import java.io.PrintStream;
import java.util.List;

public class CollectionService  implements ICommon {
    ArangoDatabase db;
    ArangoCollection collection;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    public CollectionService(ArangoDatabase db){
        this.db = db;
    }


    protected ArangoCollection collection(String collectionName){
        ArangoCollection collection;
        try {
            collection = db.collection(collectionName);
        }catch(ArangoDBException e){
            //If collection does not exist, fails silently
            out.println("Warning, collection did not exist, created.");
            createCollection(collectionName);
            collection = db.collection(collectionName);

        }
        return collection;
    }

    protected void createCollection(String collectionName) {

        try {
            CollectionEntity collection = db.createCollection(collectionName);
            out.println("Collection created: " + collection.getName());
        } catch (ArangoDBException e) {
            err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
        }
        collection = collection(collectionName);
    }

    protected void removeCollection(String collectionName){
        try {
            db.collection(collectionName).drop();
        } catch (ArangoDBException e) {
            out.println("Collection " + collectionName + "did not exist");
        }
        collection = null;
    }

    protected void resetCollection(String collectionName){
        removeCollection(collectionName);
        createCollection(collectionName);
        collection = collection(collectionName);
    }

    protected List<BaseDocument> getAll(String collectionName) {
        String query = "FOR t IN " + collectionName + " RETURN t";
        return db.query(query, null, null, BaseDocument.class).asListRemaining();
    }

    @Override
    public boolean exists(String key) {
        return collection.getDocument(key, BaseDocument.class) != null;
    }

    @Override
    public void deleteByKey(String key) {
        collection.deleteDocument(key);
    }

    @Override
    public void insert(BaseDocument document) {
        collection.insertDocument(document);
    }
}
