package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public class Gin {
    private final String NAME = "name";
    private BaseDocument document;

    public Gin(BaseDocument document){
        this.document = new BaseDocument();
        this.document.setKey(document.getKey());
        this.document.addAttribute(NAME, document.getAttribute(NAME));
    }

    public Gin(String gin){
        this.document = new BaseDocument();
        this.document.addAttribute(NAME, gin);
        this.document.setKey(gin);
    }

    public void setGin(String gin){
        this.document.addAttribute(NAME, gin);
        this.document.setKey(gin);
    }

    public String getGin(){
        return (String) document.getAttribute(NAME);
    }

    public String toString(){
        return getGin();
    }

    public BaseDocument getDocument() {
        return document;
    }
}
