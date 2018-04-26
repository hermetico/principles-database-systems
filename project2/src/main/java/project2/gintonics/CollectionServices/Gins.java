package project2.gintonics.CollectionServices;


import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.Entities.Gin;
import project2.gintonics.Utils.Utils;

import java.util.List;

public class Gins extends CollectionService{
    public static final String NAME = "gins";

    public Gins(ArangoDatabase db) {
        super(db, NAME);
    }

    public List<Gin> getAll() {
        return super.getAll( Gin.class);
    }

    public void insert(Gin gin){
        DocumentCreateEntity response = super.insert(gin);
        //gin.setKey(response.getKey());
    }

    public Gin getByKey(String key) {
        return super.getByKey(key, Gin.class);
    }

    public boolean existsByName(String name){
        return this.getByKey(Utils.getKeyFromName(name)) != null;
    }



}
