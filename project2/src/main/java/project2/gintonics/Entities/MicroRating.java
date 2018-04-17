package project2.gintonics.Entities;

public class MicroRating extends Primitive{
    private int ratingValue;

    public MicroRating(){
        super();
    }

    public MicroRating(Rating rating){
        super(rating.getName());
        this.key = rating.getKey();
        this.ratingValue = rating.getRatingValue();
    }

    public String getRatingKey() {
        return key;
    }

    public void setRatingKey(String ratingKey) {
        this.key = ratingKey;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String prettyPrint(){
        return "key: "+ key + ", name: " + name + ", rating: " + ratingValue;
    }
}
