package project2.gintonics;

import project2.gintonics.CollectionServices.*;
import project2.gintonics.Entities.*;

import java.util.List;

public class DBServices implements IDBServices {

    private DB db;
    private Gins gins;
    private Tonics tonics;
    private Garnishes garnishes;
    private Combinations combinations;
    private Users users;
    private Ratings ratings;

    public DBServices(String host, String port, String user, String password){
        db = new DB(host, port, user, password);
        db.init();
        linkServices();

    }

    public DBServices(DB db){
        this.db = db;
        this.linkServices();

    }
    private void linkServices(){
        this.gins = db.gins;
        this.tonics = db.tonics;
        this.garnishes = db.garnishes;
        this.combinations = db.combinations;
        this.users = db.users;
        this.ratings = db.ratings;
    }

    @Override
    public void resetCollections(){
        db.resetCollections();
    }

    @Override
    public boolean existsGinByName(String name) {
        return gins.existsByName(name);
    }

    @Override
    public void insertGin(Gin gin) {
        gins.insert(gin);
    }

    @Override
    public boolean existsTonicByName(String name) {
        return tonics.existsByName(name);
    }

    @Override
    public void insertTonic(Tonic tonic) {
        tonics.insert(tonic);
    }

    @Override
    public boolean existsGarnishByName(String name) {
        return garnishes.existsByName(name);
    }

    @Override
    public void insertGarnish(Garnish garnish) {
        garnishes.insert(garnish);
    }

    public DB getDb(){
        return db;
    }

    @Override
    public int getNumCombinations() {
        return combinations.getSize();
    }

    @Override
    public List<Combination> getAllCombinations() {
        return combinations.getAll();
    }

    @Override
    public void insertCombination(Combination combination) {
        combinations.insert(combination);
    }

    @Override
    public int getNumUsers(){
        return users.getSize();
    }

    @Override
    public List<User> getAllUsers() {
        return users.getAll();
    }

    @Override
    public void insertUser(User user) {
        users.insert(user);
    }


    /**
     * Creates a new rating based in the input parameters, and returns the rating
     * @param combination
     * @param user
     * @param comment
     * @param ratingValue
     * @return
     */
    @Override
    public Rating rateCombination(Combination combination, User user, String comment, int ratingValue){
        Rating rating = new Rating(combination, user, comment, ratingValue);
        ratings.insert(rating);

        MicroRating mr = new MicroRating(rating);
        user.insertMicroRating(mr);
        users.update(user);

        // we compute the new average based on the runi
        combination.updateMovingAverage(ratingValue);
        combinations.update(combination);
        return rating;

    }


}
