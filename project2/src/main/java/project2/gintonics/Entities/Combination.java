package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public class Combination {
    private final String GIN = "gin";
    private final String TONIC = "tonic";
    private final String GARNISH = "garnish";
    private final String AVG = "avg_rating";
    private final String RATINGS = "ratings";

    private BaseDocument document;

    public Combination(String gin, String tonic, String garnish){
        document = new BaseDocument();
        document.addAttribute(GIN, gin);
        document.addAttribute(TONIC, tonic);
        document.addAttribute(GARNISH, garnish);
        document.addAttribute(AVG, 0f);
        document.addAttribute(RATINGS, 0);
    }

    public Combination(String gin, String tonic){
        document = new BaseDocument();
        document.addAttribute(GIN, gin);
        document.addAttribute(TONIC, tonic);
        document.addAttribute(GARNISH, "");
        document.addAttribute(AVG, 0f);
        document.addAttribute(RATINGS, 0);
    }

    public Combination(BaseDocument document){
        this.document = new BaseDocument();

        this.document.addAttribute(GIN, document.getAttribute(GIN));
        this.document.addAttribute(TONIC, document.getAttribute(TONIC));
        this.document.addAttribute(GARNISH, document.getAttribute(GARNISH));
        this.document.addAttribute(AVG, document.getAttribute(AVG));
        this.document.addAttribute(RATINGS, document.getAttribute(RATINGS));
        this.document.setKey(document.getKey());

    }

    public String getGin(){
        return (String) document.getAttribute(GIN);
    }

    public String getTonic(){
        return (String) document.getAttribute(TONIC);
    }

    public String getGarnish(){
        return (String) document.getAttribute(GARNISH);
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
    public void setGin(String gin){
        document.addAttribute(GIN, gin);
    }

    public void setTonic(String tonic){
        document.addAttribute(TONIC, tonic);
    }

    public void setGarnish(String garnish){
        document.addAttribute(GARNISH, garnish);
    }

    public void setKey(String key){
        document.setKey(key);
    }

    public String toString() {
        String response = "";
        response += document.getAttribute(GIN) + " with " + document.getAttribute(TONIC);

        if(!document.getAttribute(GARNISH).toString().equals("")) {
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
