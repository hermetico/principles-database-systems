package project2.gintonics.Services.Ifaces;

import com.arangodb.entity.BaseDocument;

public interface ICommon {
    boolean exists(String key);
    void deleteByKey(String key);
    void insert(BaseDocument document);
}
