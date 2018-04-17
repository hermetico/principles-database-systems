package project2.gintonics.Services;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Tonic;


import java.util.List;

public class Tonics extends CollectionService{
    private static final String NAME = "tonics";

    public Tonics(ArangoDatabase db, DBService service) {
        super(db, service, NAME);
    }

    public List<Tonic> getAll() {
        return super.getAll(Tonic.class);
    }

    public void insert(Tonic tonic){
        DocumentCreateEntity response = super.insert(tonic);
        tonic.setKey(response.getKey());
    }

    public Tonic getByKey(String key) {
        return super.getByKey(key, Tonic.class);
    }


}
