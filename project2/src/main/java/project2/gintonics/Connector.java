package project2.gintonics;

import com.arangodb.*;
import project2.gintonics.CollectionServices.*;

import java.io.PrintStream;


public class Connector {
    private final String DBNAME = "gins-tonics";

    private PrintStream out = System.out;
    private PrintStream err = System.err;

    private ArangoDB arango;

    public ArangoDatabase getDb() {
        return db;
    }

    private ArangoDatabase db;
    public Gins gins;
    public Tonics tonics;
    public Garnishes garnishes;
    public Combinations combinations;
    public Users users;
    public Ratings ratings;


    public Connector(String host, String port, String user, String password){
        arango = new ArangoDB.Builder()
                .host(host)
                .port(Integer.parseInt(port))
                .user(user)
                .password(password)
                .build();


    }



    public void connect(){
        loadDB();
        init(false);
    }

    private void loadDB(){
        try{
            db = arango.db(DBNAME);
            db.getInfo();
        }catch(ArangoDBException e){
            //If db does not exist, fails silently
            err.println("Warning, database did not exist, creating it.");
            db = createDB(DBNAME);

        }

    }

    private ArangoDatabase createDB(String dbName){
        try{
            arango.createDatabase(dbName);
            return arango.db(dbName);
        }catch(ArangoDBException e){
            //If db does not exist, fails silently
            err.println("Unable to create the database " + dbName);
            e.printStackTrace();
            return null;
        }
    }

    public void resetAllCollections(){
        init(true);
    }

    public void init(boolean reset){
        gins = new Gins(db, reset);
        tonics = new Tonics(db, reset);
        garnishes = new Garnishes(db, reset);
        combinations = new Combinations(db, reset);
        users = new Users(db, reset);
        ratings = new Ratings(db, reset);
    }




}
