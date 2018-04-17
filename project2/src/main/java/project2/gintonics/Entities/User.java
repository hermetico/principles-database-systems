package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

import java.util.ArrayList;

public class User extends Primitive{
    private final String RATINGS = "ratings";

    public User(String name){
        super(name);
        document.setKey(null);
        document.addAttribute(RATINGS, new ArrayList<BaseDocument>());

    }


    public User(BaseDocument document){
        super(document);
        document.addAttribute(RATINGS, document.getAttribute(RATINGS));


    }


    public String getKey(){
        return (String) document.getKey();
    }


    public String toString() {
        String response = "";
        response += document.getAttribute(NAME);
        response +="\n\tkey: " + document.getKey();
        return response;
    }

    public BaseDocument getDocument(){
        return document;
    }


}
