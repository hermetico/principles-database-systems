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

    public Ratings(ArangoDatabase db, boolean reset) {
        super(db, NAME);
        if(reset) resetCollection();
        createIndex("combinationKey");
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

    public List<Rating> getByUserName(String userName, int page, int pageSize){
        StringBuilder builder = new StringBuilder();
        builder.append("FOR r IN @@collection\n");
        builder.append("FILTER r.userName == @userName\n");
        builder.append("SORT r.ratingValue DESC\n");
        builder.append("LIMIT " + page * pageSize + ", " + pageSize + "\n");
        builder.append("RETURN r");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@collection", NAME)
                .put("userName", userName)
                .get();

        return db.query(query, bindVars, null, Rating.class).asListRemaining();
    }

    public int getCountOfByUserName(String userName){
        StringBuilder builder = new StringBuilder();
        builder.append("Return LENGTH(\n");
            builder.append("FOR r IN @@collection\n");
            builder.append("FILTER r.userName == @userName\n");
            builder.append("SORT r.ratingValue DESC\n");
            builder.append("RETURN r\n");
        builder.append(")");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@collection", NAME)
                .put("userName", userName)
                .get();

        return db.query(query, bindVars, null, Integer.class).asListRemaining().get(0);
    }

    public List<Rating> getByUserKey(String userKey, int page, int pageSize){
        StringBuilder builder = new StringBuilder();
        builder.append("FOR r IN @@collection\n");
        builder.append("FILTER r.userKey == @userKey\n");
        builder.append("SORT r.ratingValue DESC\n");
        builder.append("LIMIT " + page * pageSize + ", " + pageSize + "\n");
        builder.append("RETURN r");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@collection", NAME)
                .put("userKey", userKey)
                .get();

        return db.query(query, bindVars, null, Rating.class).asListRemaining();
    }

    public int getCountOfByUserKey(String userKey){
        StringBuilder builder = new StringBuilder();
        builder.append("Return LENGTH(\n");
            builder.append("FOR r IN @@collection\n");
            builder.append("FILTER r.userKey == @userKey\n");
            builder.append("SORT r.ratingValue DESC\n");
            builder.append("RETURN r\n");
        builder.append(")");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@collection", NAME)
                .put("userKey", userKey)
                .get();

        return db.query(query, bindVars, null, Integer.class).asListRemaining().get(0);
    }

    /**
     * Increases the helpful count of the ratingKey given
     * and returns the updated rating
     * @param ratingKey
     * @return
     */
    public Rating increaseHelpfulCountByKey(String ratingKey){
        StringBuilder builder = new StringBuilder();
        builder.append("FOR r IN @@collection\n");
        builder.append("FILTER r._key == @ratingKey\n");
        builder.append("UPDATE r WITH {\n");
            builder.append("helpfulCount : r.helpfulCount + 1\n");
        builder.append("}\n");
        builder.append("IN ratings\n");
        builder.append("RETURN NEW\n");
        String query = builder.toString();

        Map<String, Object> bindVars = new MapBuilder()
                .put("@collection", NAME)
                .put("ratingKey", ratingKey)
                .get();

        return db.query(query, bindVars, null, Rating.class).asListRemaining().get(0);

    }

    public List<Rating> getSortedByHelpfulness(int page, int pageSize){
        StringBuilder builder = new StringBuilder();
        builder.append("FOR r IN @@collection\n");
        builder.append("SORT r.helpfulCount DESC\n");
        builder.append("LIMIT " + page * pageSize + ", " + pageSize + "\n");
        builder.append("RETURN r");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@collection", NAME)
                .get();

        return db.query(query, bindVars, null, Rating.class).asListRemaining();
    }
}
