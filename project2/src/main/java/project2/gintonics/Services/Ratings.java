package project2.gintonics.Services;



import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.DBService;
import project2.gintonics.Entities.MicroRating;
import project2.gintonics.Entities.Rating;
import project2.gintonics.Entities.User;


import java.util.List;

public class Ratings extends CollectionService {
    private static final String NAME = "ratings";

    public Ratings(ArangoDatabase db, DBService service) {
        super(db, service, NAME);
    }

    public List<Rating> getAll() {
        return super.getAll(Rating.class);
    }

    public void insert(Rating rating){
        DocumentCreateEntity response = super.insert(rating);
        rating.setKey(response.getKey());

        // updating user microrating
        //TODO move this somewhere else
        MicroRating mr = new MicroRating(rating);
        User user = service.users.getByKey(rating.getUserKey());
        user.getMicroRatings().add(mr);
        service.users.updateByKey(user.getKey(), user);
    }

    public void computeRatings(Rating rating) throws Exception {
        String query = "FOR r IN ratings " +
                "Filter r.combination_key == @combinationkey " +
                "COLLECT " +
                "total = r.rating INTO total_ratings " +
                "return {avg: average(total_ratings[*].r.rating)," +
                "total: count(total_ratings[*].rating.rating)}";
        //Map<String, Object> bindVars = new MapBuilder().put("combinationkey", rating.getCombinationKey()).get();
        //BaseDocument doc = db.query(query, bindVars, null, BaseDocument.class).asListRemaining().get(0);
        //System.out.println(doc.getProperties());

        throw new Exception("Not implemented");
    }
    public Rating getByKey(String key) {
        return super.getByKey(key, Rating.class);
    }


}
