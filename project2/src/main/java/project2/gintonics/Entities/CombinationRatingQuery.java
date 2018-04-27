package project2.gintonics.Entities;

import java.util.List;

public class CombinationRatingQuery {
    private Combination combination;
    private Rating rating;
    public CombinationRatingQuery(){
        super();
    }

    public Combination getCombination() {
        return combination;
    }

    public Rating getRating() {
        return rating;
    }

    public String prettyPrint(){
        StringBuilder response = new StringBuilder();
        response.append(combination.prettyPrint());
        response.append(rating.prettyPrint());
        return response.toString();
    }
}
