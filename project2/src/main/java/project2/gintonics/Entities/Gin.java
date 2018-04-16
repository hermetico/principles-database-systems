package project2.gintonics.Entities;

import com.arangodb.entity.BaseDocument;

public class Gin  extends Primitive{
    public Gin(BaseDocument document){
        super(document);

    }

    public Gin(String gin){
        super(gin);

    }
}
