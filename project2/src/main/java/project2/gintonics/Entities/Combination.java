package project2.gintonics.Entities;

public class Combination extends Primitive {
    private String gin;
    private String tonic;
    private String garnish;
    private float avg;
    private int numRatings;

    public Combination(){
        super();
    }
    public Combination(Gin gin, Tonic tonic, Garnish garnish){
        super(gin.getName() + " with " + tonic.getName() + " and " + garnish.getName());
        this.gin = gin.getName();
        this.tonic = tonic.getName();
        this.garnish = garnish.getName();
        this.avg = 0f;
        this.numRatings = 0;
    }

    public Combination(Gin gin, Tonic tonic){
        super(gin.getName() + " with " + tonic.getName());
        this.gin = gin.getName();
        this.tonic = tonic.getName();
        this.avg = 0f;
        this.numRatings = 0;

    }

    public Combination(Combination other){
        super(other);
        this.gin = other.gin;
        this.tonic = other.tonic;
        this.garnish = other.garnish;
        this.avg = other.avg;
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

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }
}
