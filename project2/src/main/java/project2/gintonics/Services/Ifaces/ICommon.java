package project2.gintonics.Services.Ifaces;

import com.arangodb.entity.BaseDocument;
import project2.gintonics.Entities.Primitive;

public interface ICommon {
    boolean exists(Primitive primitive);
    boolean existsByKey(String key);
    void delete(Primitive primitive);
    void deleteByKey(String key);
    void insert(BaseDocument document);
}
