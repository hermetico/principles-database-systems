package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public class Tonic extends Primitive{
    public Tonic(BaseDocument document){
        super(document);

    }

    public Tonic(String tonic){
        super(tonic);

    }

}
