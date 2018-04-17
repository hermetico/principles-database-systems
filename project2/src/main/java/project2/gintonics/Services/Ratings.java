package project2.gintonics.Services;


import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Rating;


import java.util.List;
import java.util.Map;

public class Ratings extends CollectionService {
    private final String NAME = "ratings";

    public Ratings(ArangoDatabase db, DBService service) {
        super(db, service);
        collection = collection(NAME);
    }

    public void resetCollection(){
        resetCollection(NAME);
    }

    public List<BaseDocument> getAll() {
        return super.getAll(NAME);
    }

    public int getSize(){return super.getSize(NAME);}

    public void insert(Rating rating){
        String ratingId  = String.valueOf(getSize(NAME) + 1);
        rating.setKey(ratingId);
        super.insert(rating.getDocument());
        this.computeRatings(rating);
    }

    public void computeRatings(Rating rating){
        String query = "FOR r IN ratings " +
                "Filter r.combination_key == @combinationkey " +
                "COLLECT " +
                "total = r.rating INTO total_ratings " +
                "return {avg: average(total_ratings[*].r.rating)," +
                "total: count(total_ratings[*].rating.rating)}";
        Map<String, Object> bindVars = new MapBuilder().put("combinationkey", rating.getCombinationKey()).get();
        BaseDocument doc = db.query(query, bindVars, null, BaseDocument.class).asListRemaining().get(0);
        System.out.println(doc.getProperties());
    }
    public Rating getByKey(String key) {
        BaseDocument doc = collection.getDocument(key, BaseDocument.class);
        if (doc == null ){
            return null;
        }
        return new Rating(doc);
    }


}
