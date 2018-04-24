package project2.gintonics.Entities;

import project2.gintonics.Utils.Utils;

public class Tonic extends Primitive {

    public Tonic(){
        super();
    }

    public Tonic(Tonic other){
        super(other);
    }

    public Tonic(String name){
        super(name);
        this.key  = Utils.getKeyFromName(name);
    }

}
