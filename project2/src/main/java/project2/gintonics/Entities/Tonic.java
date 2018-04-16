package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public class Tonic {
    private final String NAME = "name";
    private BaseDocument document;

    public Tonic(BaseDocument document){
        this.document = new BaseDocument();
        this.document.setKey(document.getKey());
        this.document.addAttribute(NAME, document.getAttribute(NAME));
    }

    public Tonic(String tonic){
        this.document = new BaseDocument();
        this.document.addAttribute(NAME, tonic);
        this.document.setKey(tonic);
    }

    public void setTonic(String tonic){
        this.document.addAttribute(NAME, tonic);
        this.document.setKey(tonic);
    }

    public String getTonic(){
        return (String) document.getAttribute(NAME);
    }

    public String toString(){
        return getTonic();
    }

    public BaseDocument getDocument() {
        return document;
    }
}
