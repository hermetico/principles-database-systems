package project2.gintonics.CollectionServices;
import com.arangodb.entity.DocumentCreateEntity;

import java.util.List;

public interface ICollectionService {
    boolean exists(String key);
    boolean existsByName(String name);
    <T> List<T> getAll(Class<T> asType);
    int getSize();
    void delete(String key);
    void deleteByKey(String key);
    <T> T getByKey(String key, Class<T> asType);
    <T> DocumentCreateEntity insert(T obj);
    String getCollectionName();
}
