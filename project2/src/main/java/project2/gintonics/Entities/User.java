package project2.gintonics.Entities;

import java.util.ArrayList;
import java.util.List;

public class User extends Primitive {
    private List<MicroRating> microRatings;

    public User(){
        super();
    }

    public User(String name){
        super(name);
        microRatings = new ArrayList<MicroRating>();
    }

    public List<MicroRating> getMicroRatings() {
        return microRatings;
    }

    public void insertMicroRating(MicroRating mr){
        this.microRatings.add(mr);
    }

    public void setMicroRatings(List<MicroRating> microRatings) {
        this.microRatings = microRatings;
    }

    public int getNumRatings(){
        return microRatings.size();
    }

    public String prettyPrint(){
        StringBuilder response = new StringBuilder();
        response.append(name);
        if(microRatings.size() > 0){
            response.append("\n\tMicroRatings:");
            for(MicroRating mr: microRatings){
                response.append("\n\t" + mr.prettyPrint());
            }
        }
        return response.toString();
    }

}
