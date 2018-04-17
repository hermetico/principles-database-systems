package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public class Combination extends Primitive{
    private final String GIN = "gin";
    private final String TONIC = "tonic";
    private final String GARNISH = "garnish";
    private final String AVG = "avg_rating";
    private final String RATINGS = "ratings";

    public Combination(Gin gin, Tonic tonic, Garnish garnish){
        super(gin + " " + tonic + " " + garnish);
        document.addAttribute(GIN, gin.getName());
        document.addAttribute(TONIC, tonic.getName());
        document.addAttribute(GARNISH, garnish.getName());
        document.addAttribute(AVG, 0f);
        document.addAttribute(RATINGS, 0);

    }

    public Combination(Gin gin, Tonic tonic){
        super(gin + " " + tonic);
        document.addAttribute(GIN, gin.getName());
        document.addAttribute(TONIC, tonic.getName());
        document.addAttribute(GARNISH, null);
        document.addAttribute(AVG, 0f);
        document.addAttribute(RATINGS, 0);

    }

    public Combination(BaseDocument document){
        super(document);
        this.document.addAttribute(GIN, document.getAttribute(GIN));
        this.document.addAttribute(TONIC, document.getAttribute(TONIC));
        this.document.addAttribute(GARNISH, document.getAttribute(GARNISH));
        this.document.addAttribute(AVG, document.getAttribute(AVG));
        this.document.addAttribute(RATINGS, document.getAttribute(RATINGS));
        this.document.setKey(document.getKey());

    }

    public Gin getGin(){
        return new Gin(document.getAttribute(GIN).toString());
    }

    public Tonic getTonic(){
        return new Tonic(document.getAttribute(TONIC).toString());
    }

    public Garnish getGarnish(){
        return new Garnish(document.getAttribute(GARNISH).toString());
    }

    public float getAverageRating(){
        return (float) document.getAttribute(AVG);
    }

    public int getNumRatings(){
        return (int) document.getAttribute(RATINGS);
    }

    public String getKey(){
        return (String) document.getKey();
    }

    public void setGin(Gin gin){
        document.addAttribute(GIN, gin.toString());
    }

    public void setTonic(Tonic tonic){
        document.addAttribute(TONIC, tonic.toString());
    }

    public void setGarnish(Garnish garnish){
        document.addAttribute(GARNISH, garnish.toString());
    }

    public String toString() {
        String response = "";
        response += document.getAttribute(GIN) + " with " + document.getAttribute(TONIC);

        if(document.getAttribute(GARNISH) != null) {
            response += "\n\tgarnish: " + document.getAttribute(GARNISH);
        }
        response += "\n\taverage rating: " + document.getAttribute(AVG)
                + "\n\tnum rating: " + document.getAttribute(RATINGS)
                + "\n\tkey: " + document.getKey();
        return response;
    }

    public BaseDocument getDocument(){
        return document;
    }


}
