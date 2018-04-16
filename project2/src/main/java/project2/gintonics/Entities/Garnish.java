package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public class Garnish {
    private final String NAME = "name";
    private BaseDocument document;

    public Garnish(BaseDocument document){
        this.document = new BaseDocument();
        this.document.setKey(document.getKey());
        this.document.addAttribute(NAME, document.getAttribute(NAME));
    }

    public Garnish(String garnish){
        this.document = new BaseDocument();
        this.document.addAttribute(NAME, garnish);
        this.document.setKey(garnish);
    }

    public void setGarnish(String garnish){
        this.document.addAttribute(NAME, garnish);
        this.document.setKey(garnish);
    }

    public String getGarnish(){
        return (String) document.getAttribute(NAME);
    }

    public String toString(){
        return getGarnish();
    }

    public BaseDocument getDocument() {
        return document;
    }
}
