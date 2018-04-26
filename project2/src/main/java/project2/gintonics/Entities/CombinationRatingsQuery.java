package project2.gintonics.Entities;

import java.util.List;

public class CombinationRatingsQuery {
    private Combination combination;
    private List<Rating> ratings;
    public CombinationRatingsQuery(){
        super();
    }

    public Combination getCombination() {
        return combination;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public String prettyPrint(){
        StringBuilder response = new StringBuilder();
        response.append(combination.prettyPrint());
        if(ratings.size() > 0){
            response.append("\n\tRatings:");
            for(Rating rating: ratings){
                response.append("\n\t" + rating.prettyPrint());
            }
        }
        return response.toString();
    }
}
