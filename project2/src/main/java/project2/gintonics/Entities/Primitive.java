package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public abstract class Primitive {
    protected BaseDocument document;
    protected final String NAME = "name";

    public Primitive(BaseDocument document){
        this.document = new BaseDocument();
        this.document.addAttribute(NAME, document.getAttribute(NAME));
        this.document.setKey(document.getKey());

    }

    public Primitive(String name){
        this.document = new BaseDocument();
        this.document.addAttribute(NAME, name);
        this.document.setKey(name.replace(" ", "-"));

    }

    public void setName(String name){
        this.document.addAttribute(NAME, name);
        this.document.setKey(name.replace(" ", "-"));
    }

    public String getName(){
        return (String) document.getAttribute(NAME);
    }

    public String getKey(){
        return document.getKey();
    }
    public String toString(){
        return getName();
    }

    public void setKey(String key){
        document.setKey(key);
    }
    public BaseDocument getDocument() {
        return document;
    }
}
