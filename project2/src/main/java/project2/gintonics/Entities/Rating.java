package project2.gintonics.Entities;

import java.util.List;

public class Rating extends Primitive {
    private String combinationKey;
    private String userKey;
    private String userName;
    private String comments;
    private int ratingValue;
    private int helpfulCount;

    public Rating(){
        super();
    }

    public Rating(Combination combination, User user, String comments, int ratingValue){
        super(combination.getName());
        this.combinationKey = combination.getKey();
        this.userKey = user.getKey();
        this.userName = user.getName();
        this.comments = comments;
        this.ratingValue = ratingValue;
        this.helpfulCount = 0;
    }

    public String getCombinationKey() {
        return combinationKey;
    }

    public void setCombinationKey(String combinationKey) {
        this.combinationKey = combinationKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public int getHelpfulCount() {
        return helpfulCount;
    }

    public void setHelpfulCount(int helpfulCount) {
        this.helpfulCount = helpfulCount;
    }


    public String prettyPrint(){
        StringBuilder response = new StringBuilder();
        response.append("Rating: "  + name);
        response.append("\n\tkey: "  + key);
        response.append("\n\tcombinationKey: "  + combinationKey);
        response.append("\n\tuserName: "  + userName);
        response.append("\n\tuserKey: "  + userKey);
        response.append("\n\tratingValue: "  + ratingValue);
        response.append("\n\tcomments: "  + comments);
        return response.toString();
    }
}
