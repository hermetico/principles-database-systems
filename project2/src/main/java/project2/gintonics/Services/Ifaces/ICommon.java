package project2.gintonics.Services.Ifaces;

import com.arangodb.entity.BaseDocument;
import project2.gintonics.Entities.Primitive;

import java.util.List;

public interface ICommon {
    boolean exists(Primitive primitive);
    boolean existsByKey(String key);
    List<BaseDocument> getAll(String collectionName);
    int getSize(String collectionName);
    void delete(Primitive primitive);
    void deleteByKey(String key);
    void insert(BaseDocument document);
}
