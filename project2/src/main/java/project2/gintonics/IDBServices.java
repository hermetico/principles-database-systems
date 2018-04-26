package project2.gintonics;

import project2.gintonics.Entities.*;

import java.util.List;

public interface IDBServices {
    void resetCollections();

    // gins
    boolean existsGinByName(String name);
    void insertGin(Gin gin);

    // tonics
    boolean existsTonicByName(String name);
    void insertTonic(Tonic tonic);

    // garnishes
    boolean existsGarnishByName(String name);
    void insertGarnish(Garnish garnish);

    // combinations
    int getNumCombinations();
    List<Combination> getAllCombinations();
    void insertCombination(Combination combination);

    // users
    int getNumUsers();
    List<User> getAllUsers();
    void insertUser(User user);

    // ratings
    Rating rateCombination(Combination combination, User user, String comment, int value);

}
