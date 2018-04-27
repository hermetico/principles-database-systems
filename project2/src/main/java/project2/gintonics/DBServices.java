package project2.gintonics;

import com.arangodb.ArangoDatabase;
import com.arangodb.util.MapBuilder;
import project2.gintonics.CollectionServices.*;
import project2.gintonics.Entities.*;
import project2.gintonics.Utils.Constraints;

import java.util.List;
import java.util.Map;

public class DBServices implements IDBServices {

    private Connector connector;
    private Gins gins;
    private Tonics tonics;
    private Garnishes garnishes;
    private Combinations combinations;
    private Users users;
    private Ratings ratings;
    private ArangoDatabase db;

    public DBServices(String host, String port, String user, String password){
        connector = new Connector(host, port, user, password);
        connector.init();
        linkServices();

    }

    public DBServices(Connector connector){
        this.connector = connector;
        this.linkServices();

    }

    public Connector getConnector(){
        return connector;
    }

    private void linkServices(){
        this.db = connector.getDb();
        this.gins = connector.gins;
        this.tonics = connector.tonics;
        this.garnishes = connector.garnishes;
        this.combinations = connector.combinations;
        this.users = connector.users;
        this.ratings = connector.ratings;
    }

    @Override
    public void resetCollections(){
        connector.resetAllCollections();
    }

    @Override
    public boolean existsGinByName(String name) {
        return gins.existsByName(name);
    }

    @Override
    public List<Gin> getAllGins() {
        return gins.getAll();
    }

    @Override
    public void insertGin(Gin gin) {
        gins.insert(gin);
    }

    @Override
    public void resetGinsCollection() {
        gins.resetCollection();
    }

    @Override
    public boolean existsTonicByName(String name) {
        return tonics.existsByName(name);
    }

    @Override
    public List<Tonic> getAllTonics() {
        return tonics.getAll();
    }

    @Override
    public void insertTonic(Tonic tonic) {
        tonics.insert(tonic);
    }

    @Override
    public void resetTonicsCollection() {
        tonics.resetCollection();
    }

    @Override
    public boolean existsGarnishByName(String name) {
        return garnishes.existsByName(name);
    }

    @Override
    public List<Garnish> getAllGarnishes() {
        return garnishes.getAll();
    }

    @Override
    public void insertGarnish(Garnish garnish) {
        garnishes.insert(garnish);
    }

    @Override
    public void resetGarnishesCollection() {
        garnishes.resetCollection();
    }

    @Override
    public boolean existsCombination(Combination combination) {
        return existsCombinationByGinAndTonicAndGarnish(combination.getGin(), combination.getTonic(), combination.getGarnish());
    }

    @Override
    public boolean existsCombinationByGinAndTonic(String gin, String tonic) {
        return existsCombinationByGinAndTonicAndGarnish(gin, tonic, null);
    }

    @Override
    public boolean existsCombinationByGinAndTonicAndGarnish(String gin, String tonic, String garnish) {
        return combinations.existsByGinAndTonicAndGarnish(gin, tonic, garnish);
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
    public Combination getCombinationByKey(String key) {
        return combinations.getByKey(key);
    }

    @Override
    public void resetCombinationsCollection() {
        combinations.resetCollection();
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

    @Override
    public void resetUsersCollection() {
        users.resetCollection();
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

        combination.updateMovingAverage(ratingValue);
        combinations.update(combination);
        return rating;

    }

    @Override
    public List<Rating> getRatingsByCombination(Combination combination) {
        return getRatingsByCombination(combination, 0, Constraints.MAXRECORDS);
    }

    @Override
    public List<Rating> getRatingsByCombination(Combination combination, int page, int pageSize) {
        return ratings.getByCombinationKey(combination, page, pageSize);
    }

    @Override
    public void resetRatingsCollection() {
        ratings.resetCollection();
    }

    @Override
    public int getCountOfRatingsByGinAndTonic(String gin, String tonic) {
        StringBuilder builder = new StringBuilder();
        builder.append("Return LENGTH(\n");
            builder.append("FOR c IN @@leftCollection\n");
            builder.append("FILTER c.gin == @gin\n");
            builder.append("FILTER c.tonic == @tonic\n");
                builder.append("FOR r IN @@rightCollection\n");
                builder.append("FILTER r.combinationKey == c._key\n");
            builder.append("RETURN {\n");
                builder.append("combination: c,\n");
                builder.append("rating: r\n");
            builder.append("}\n");
        builder.append(")");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@leftCollection", combinations.getCollectionName())
                .put("@rightCollection", ratings.getCollectionName())
                .put("gin", gin)
                .put("tonic", tonic)
                .get();

        return db.query(query, bindVars, null, Integer.class).asListRemaining().get(0);
    }

    @Override
    public int getCountOfRatingsByGinAndTonicAndGarnish(String gin, String tonic, String garnish) {
        StringBuilder builder = new StringBuilder();
        builder.append("Return LENGTH(\n");
            builder.append("FOR c IN @@leftCollection\n");
            builder.append("FILTER c.gin == @gin\n");
            builder.append("FILTER c.tonic == @tonic\n");
            builder.append("FILTER c.garnish == @garnish\n");
                builder.append("FOR r IN @@rightCollection\n");
                builder.append("FILTER r.combinationKey == c._key\n");
            builder.append("RETURN {\n");
                builder.append("combination: c,\n");
                builder.append("rating: r\n");
            builder.append("}\n");
        builder.append(")");

        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@leftCollection", combinations.getCollectionName())
                .put("@rightCollection", ratings.getCollectionName())
                .put("gin", gin)
                .put("tonic", tonic)
                .put("garnish", garnish)
                .get();

        return db.query(query, bindVars, null, Integer.class).asListRemaining().get(0);
    }

    @Override
    public List<CombinationRatingQuery> getRatingsByGinAndTonic(String gin, String tonic) {
        return getRatingsByGinAndTonic(gin, tonic, 0, Constraints.MAXRECORDS);
    }

    @Override
    public List<CombinationRatingQuery> getRatingsByGinAndTonicAndGarnish(String gin, String tonic, String garnish) {
        return getRatingsByGinAndTonicAndGarnish(gin, tonic, garnish, 0, Constraints.MAXRECORDS);
    }

    @Override
    public List<CombinationRatingQuery> getRatingsByGinAndTonic(String gin, String tonic, int page, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("FOR c IN @@leftCollection\n");
        builder.append("FILTER c.gin == @gin\n");
        builder.append("FILTER c.tonic == @tonic\n");
            builder.append("FOR r IN @@rightCollection\n");
            builder.append("FILTER r.combinationKey == c._key\n");
        builder.append("LIMIT " + page * pageSize + ", " + pageSize + "\n");
        builder.append("RETURN {\n");
            builder.append("combination: c,\n");
            builder.append("rating: r\n");
        builder.append("}\n");
        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@leftCollection", combinations.getCollectionName())
                .put("@rightCollection", ratings.getCollectionName())
                .put("gin", gin)
                .put("tonic", tonic)
                .get();
        return db.query(query, bindVars, null, CombinationRatingQuery.class).asListRemaining();

    }

    @Override
    public List<CombinationRatingQuery> getRatingsByGinAndTonicAndGarnish(String gin, String tonic, String garnish, int page, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("FOR c IN @@leftCollection\n");
        builder.append("FILTER c.gin == @gin\n");
        builder.append("FILTER c.tonic == @tonic\n");
        builder.append("FILTER c.garnish == @garnish\n");
            builder.append("FOR r IN @@rightCollection\n");
            builder.append("FILTER r.combinationKey == c._key\n");
        builder.append("LIMIT " + page * pageSize + ", " + pageSize + "\n");
        builder.append("RETURN {\n");
            builder.append("combination: c,\n");
            builder.append("rating: r\n");
        builder.append("}\n");
        String query = builder.toString();
        Map<String, Object> bindVars = new MapBuilder()
                .put("@leftCollection", combinations.getCollectionName())
                .put("@rightCollection", ratings.getCollectionName())
                .put("gin", gin)
                .put("tonic", tonic)
                .put("garnish", garnish)
                .get();

        return db.query(query, bindVars, null, CombinationRatingQuery.class).asListRemaining();
    }


}
