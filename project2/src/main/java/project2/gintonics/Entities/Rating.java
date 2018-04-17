package project2.gintonics.Entities;

public class Rating extends Primitive {
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
        userKey = user.getKey();
        userName = user.getName();
        this.comments = comments;
        this.ratingValue = ratingValue;
        this.helpfulCount = 0;
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
        String response = "";
        response += "key: "+ key + "\n\tname: " + name;
        response += "\n\tuserKey: " + userKey +"\n\tuserName: " + userName;
        response += "\n\tvalue: " + ratingValue + "\n\thelpfulCount: " + helpfulCount;
        return response;
    }
}
