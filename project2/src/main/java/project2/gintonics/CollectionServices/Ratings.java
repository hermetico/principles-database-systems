package project2.gintonics.CollectionServices;



import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.util.MapBuilder;
import project2.gintonics.Entities.Combination;
import project2.gintonics.Entities.Rating;


import java.util.List;
import java.util.Map;

public class Ratings extends CollectionService {
    private static final String NAME = "ratings";

    public Ratings(ArangoDatabase db) {
        super(db, NAME);
    }

    public List<Rating> getAll() {
        return super.getAll(Rating.class);
    }

    /**
     * Inserts a new rating in the collection
     * @param rating : new rating to insert, its id gets populated after inserting the rating
     */
    public void insert(Rating rating){
        DocumentCreateEntity response = super.insert(rating);
        rating.setKey(response.getKey());
    }

    public Rating getByKey(String key) {
        return super.getByKey(key, Rating.class);
    }

    public List<Rating> getByCombinationKey(Combination combination, int page, int pageSize){
        StringBuilder builder = new StringBuilder();
        builder.append("FOR r IN @@collection\n");
        builder.append("FILTER r.combinationKey == @combinationKey\n");
        builder.append("LIMIT " + page * pageSize + ", " + pageSize + "\n");
        builder.append("RETURN r");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@collection", NAME)
                .put("combinationKey", combination.getKey().toString())
                .get();

        return db.query(query, bindVars, null, Rating.class).asListRemaining();
    }

    public int getCountOfByCombinationKey(Combination combination){
        StringBuilder builder = new StringBuilder();
        builder.append("Return LENGTH(\n");
            builder.append("FOR r IN @@collection\n");
            builder.append("FILTER r.combinationKey == @combinationKey\n");
            builder.append("RETURN r\n");
        builder.append(")");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@collection", NAME)
                .put("combinationKey", combination.getKey().toString())
                .get();

        return db.query(query, bindVars, null, Integer.class).asListRemaining().get(0);
    }

}
