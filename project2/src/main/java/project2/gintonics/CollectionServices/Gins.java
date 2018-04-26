package project2.gintonics.Services;


import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Gin;
import java.util.List;

public class Gins extends CollectionService{
    public static final String NAME = "gins";

    public Gins(ArangoDatabase db, DBService service) {
        super(db, service, NAME);
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



}
