package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

import java.util.ArrayList;

public class Rating extends Primitive{
    private final String COMBINATIONKEY = "combination_key";
    private final String USERKEY = "user_key";
    private final String USERNAME = "user_name";
    private final String COMMENT = "comment";
    private final String RATING = "rating";
    private final String HELPFUL = "helpful";

    public Rating(Combination combination, User user, String comment, int rating){
        super(combination.getName());
        document.setKey(null);
        document.addAttribute(COMBINATIONKEY, combination.getKey());
        document.addAttribute(USERKEY, user.getKey());
        document.addAttribute(USERNAME, user.getName());
        document.addAttribute(COMMENT, comment);
        document.addAttribute(RATING, rating);
        document.addAttribute(HELPFUL, 0);

    }


    public Rating(BaseDocument document){
        super(document);
        this.document.addAttribute(COMBINATIONKEY, document.getAttribute(COMBINATIONKEY));
        this.document.addAttribute(USERKEY, document.getAttribute(USERKEY));
        this.document.addAttribute(USERNAME, document.getAttribute(USERNAME));
        this.document.addAttribute(COMMENT, document.getAttribute(COMMENT));
        this.document.addAttribute(RATING, document.getAttribute(RATING));
        this.document.addAttribute(HELPFUL, document.getAttribute(HELPFUL));
    }


    public String getKey(){
        return (String) document.getKey();
    }


    public String getUserKey(){
        return document.getAttribute(USERKEY).toString();
    }
    public String getCombinationKey(){
        return document.getAttribute(COMBINATIONKEY).toString();
    }

    public String getUserName(){
        return document.getAttribute(USERNAME).toString();
    }

    public String toString() {
        String response = "";
        response += "Rating by: " + document.getAttribute(USERNAME);
        response += "\n\tTotal: " + document.getAttribute(RATING);
        response += "\n\tComment: "+ document.getAttribute(COMMENT);
        response += "\n\tCombination_key: "+ document.getAttribute(COMBINATIONKEY);
        response += "\n\tUser_key: "+ document.getAttribute(USERKEY);
        response += "\n\tHelpful: "+ document.getAttribute(HELPFUL);
        response += "\n\tkey: "+ document.getKey();
        return response;
    }

    public BaseDocument getDocument(){
        return document;
    }


}
