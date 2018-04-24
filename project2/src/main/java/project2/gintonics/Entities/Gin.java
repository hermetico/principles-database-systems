package project2.gintonics.Entities;


import project2.gintonics.Utils.Utils;

public class Gin extends Primitive{

    public Gin(){
        super();
    }

    public Gin(Gin other){
        super(other);
    }

    public Gin(String name){
        super(name);
        this.key  = Utils.getKeyFromName(name);
    }

}
