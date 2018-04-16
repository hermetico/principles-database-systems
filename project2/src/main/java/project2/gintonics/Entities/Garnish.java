package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public class Garnish extends Primitive{
    public Garnish(BaseDocument document){
        super(document);

    }

    public Garnish(String garnish){
        super(garnish);

    }
}
