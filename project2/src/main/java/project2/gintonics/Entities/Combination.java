package project2.gintonics.Entities;

import project2.gintonics.Utils.Utils;

public class Combination extends Primitive {
    private String gin;
    private String tonic;
    private String garnish;
    private float avgRating;
    private int numRatings;

    public Combination(){
        super();
    }

    public Combination(Gin gin, Tonic tonic, Garnish garnish){
        super(Utils.getCombinationName(gin.getName(), tonic.getName(), garnish.getName()));
        this.gin = gin.getName();
        this.tonic = tonic.getName();
        this.garnish = garnish.getName();
        this.avgRating = 0f;
        this.numRatings = 0;
    }

    public Combination(Gin gin, Tonic tonic){
        super(Utils.getCombinationName(gin.getName(), tonic.getName()));
        this.gin = gin.getName();
        this.tonic = tonic.getName();
        this.avgRating = 0f;
        this.numRatings = 0;

    }

    public Combination(Combination other){
        super(other);
        this.gin = other.gin;
        this.tonic = other.tonic;
        this.garnish = other.garnish;
        this.avgRating = other.avgRating;
        this.numRatings = other.numRatings;
    }

    public String getGin() {
        return gin;
    }

    public void setGin(String gin) {
        this.gin = gin;
    }

    public String getTonic() {
        return tonic;
    }

    public void setTonic(String tonic) {
        this.tonic = tonic;
    }

    public String getGarnish() {
        return garnish;
    }

    public void setGarnish(String garnish) {
        this.garnish = garnish;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public int getNumRatings() {
        return numRatings;
    }


    public void updateMovingAverage(int newRating){
        if(this.numRatings == 0){
            this.avgRating = newRating;
        }else{
            float avg = this.avgRating * this.numRatings;
            this.avgRating = (avg + newRating) / (this.numRatings + 1);
        }
        this.numRatings++;
    }

    public String prettyPrint(){
        StringBuilder response =  new StringBuilder();
        response.append("Combination: " + name);
        response.append("\n\tid: " + key);
        response.append("\n\tgin: " + gin);
        response.append("\n\ttonic: " + tonic);
        if(garnish != null)
            response.append("\n\tgarnish: " + garnish);
        response.append("\n\tAverage rating: " + avgRating);
        response.append("\n\tNum ratings: " + numRatings);

        return response.toString();
    }
}
