package project2.gintonics.Entities;
import com.arangodb.entity.DocumentField.Type;
import com.arangodb.entity.DocumentField;

public abstract class Primitive {

    @DocumentField(Type.KEY)
    protected String key;
    protected String name;

    public Primitive(){
        super();
    }

    public Primitive(Primitive other){
        this.key = other.key;
        this.name = other.name;
    }

    public Primitive(String name){
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String prettyPrint(){return name;}
}

