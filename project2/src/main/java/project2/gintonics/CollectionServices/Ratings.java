package project2.gintonics.CollectionServices;



import com.arangodb.ArangoDatabase;
import com.arangodb.entity.DocumentCreateEntity;
import project2.gintonics.DB;
import project2.gintonics.Entities.MicroRating;
import project2.gintonics.Entities.Rating;
import project2.gintonics.Entities.User;


import java.util.List;

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


}
