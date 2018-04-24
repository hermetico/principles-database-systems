package project2.gintonics.Entities;

import project2.gintonics.Utils.Utils;

public class Garnish extends Primitive {

    public Garnish(){
        super();
    }

    public Garnish(Garnish other){
        super(other);

    }

    public Garnish(String name){
        super(name);
        this.key  = Utils.getKeyFromName(name);

    }
}
