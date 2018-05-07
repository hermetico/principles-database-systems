package project2.gintonics.CollectionServices;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.Entities.Tonic;
import project2.gintonics.Utils.Utils;


import java.util.List;

public class Tonics extends CollectionService{
    private static final String NAME = "tonics";

    public Tonics(ArangoDatabase db, boolean reset) {
        super(db, NAME);
        if(reset) resetCollection();
    }

    public List<Tonic> getAll() {
        return super.getAll(Tonic.class);
    }

    public void insert(Tonic tonic){
        DocumentCreateEntity response = super.insert(tonic);
        //tonic.setKey(response.getKey());
    }

    public Tonic getByKey(String key) {
        return super.getByKey(key, Tonic.class);
    }

    public boolean existsByName(String name){
        return this.getByKey(Utils.getKeyFromName(name)) != null;
    }


}
