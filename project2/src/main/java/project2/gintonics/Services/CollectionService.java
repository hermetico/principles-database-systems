package project2.gintonics.Services;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.CollectionEntity;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Primitive;
import project2.gintonics.Services.Ifaces.ICommon;

import java.io.PrintStream;
import java.util.List;

public class CollectionService  implements ICommon {
    protected ArangoDatabase db;
    protected ArangoCollection collection;
    protected DBService service;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    public CollectionService(ArangoDatabase db, DBService service){
        this.service = service;
        this.db = db;
    }


    protected ArangoCollection collection(String collectionName){
        ArangoCollection collection;
        try {
            collection = db.collection(collectionName);
            collection.load();
        }catch(ArangoDBException e){
            //If collection does not exist, fails silently
            out.println("Warning, collection "+ collectionName +" did not exist, created.");
            createCollection(collectionName);
            collection = db.collection(collectionName);

        }
        return collection;
    }

    protected void createCollection(String collectionName) {

        try {
            db.createCollection(collectionName);
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

    public List<BaseDocument> getAll(String collectionName) {
        String query = "FOR t IN " + collectionName + " RETURN t";
        return db.query(query, null, null, BaseDocument.class).asListRemaining();
    }

    public int getSize(String collectionName) {
        String query = "FOR t IN " + collectionName + " COLLECT WITH COUNT INTO number RETURN {total:number}";
        String size = db.query(query, null, null, BaseDocument.class)
                .asListRemaining()
                .get(0)
                .getAttribute("total")
                .toString();
        return Integer.parseInt(size);
    }

    @Override
    public boolean exists(Primitive primitive) {
        return collection.getDocument(primitive.getKey(), BaseDocument.class) != null;
    }

    @Override
    public boolean existsByKey(String key) {
        return collection.getDocument(key, BaseDocument.class) != null;
    }

    @Override
    public void delete(Primitive primitive) {
        collection.deleteDocument(primitive.getKey());
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
