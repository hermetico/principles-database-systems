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

    public void setMicroRatings(List<MicroRating> microRatings) {
        this.microRatings = microRatings;
    }

    public String prettyPrint(){
        String response = "";
        response += name;
        if(microRatings.size() > 0){
            response += "\n\tMicroratings:";
            for(MicroRating mr: microRatings){
                response += "\n\t" + mr.prettyPrint();
            }

        }

        return response;
    }
}
