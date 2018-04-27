package project2.gintonics;

import project2.gintonics.Entities.*;

import java.util.List;

public interface IDBServices {
    void resetCollections();

    // gins
    boolean existsGinByName(String name);
    List<Gin> getAllGins();
    void insertGin(Gin gin);
    void resetGinsCollection();

    // tonics
    boolean existsTonicByName(String name);
    List<Tonic> getAllTonics();
    void insertTonic(Tonic tonic);
    void resetTonicsCollection();

    // garnishes
    boolean existsGarnishByName(String name);
    List<Garnish> getAllGarnishes();
    void insertGarnish(Garnish garnish);
    void resetGarnishesCollection();

    // combinations
    boolean existsCombination(Combination combination);
    boolean existsCombinationByGinAndTonic(String gin, String tonic);
    boolean existsCombinationByGinAndTonicAndGarnish(String gin, String tonic, String garnish);
    int getNumCombinations();
    List<Combination> getAllCombinations();
    void insertCombination(Combination combination);
    Combination getCombinationByKey(String key);
    void resetCombinationsCollection();

    // users
    int getNumUsers();
    List<User> getAllUsers();
    void insertUser(User user);
    void resetUsersCollection();

    // ratings
    Rating rateCombination(Combination combination, User user, String comment, int value);
    List<Rating> getRatingsByCombination(Combination combination);
    List<Rating> getRatingsByCombination(Combination combination, int page, int pageSize);
    void resetRatingsCollection();

    // CombinationRatingQuery
    int getCountOfRatingsByGinAndTonic(String gin, String tonic);
    int getCountOfRatingsByGinAndTonicAndGarnish(String gin, String tonic, String garnish);
    List<CombinationRatingQuery> getRatingsByGinAndTonic(String gin, String tonic);
    List<CombinationRatingQuery> getRatingsByGinAndTonicAndGarnish(String gin, String tonic, String garnish);
    List<CombinationRatingQuery> getRatingsByGinAndTonic(String gin, String tonic, int page, int pageSize);
    List<CombinationRatingQuery> getRatingsByGinAndTonicAndGarnish(String gin, String tonic, String garnish, int page, int pageSize);

}
