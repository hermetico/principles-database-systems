package project2.gintonics;

import com.arangodb.*;
import project2.gintonics.Services.*;

import java.io.PrintStream;


public class DBService{
    private final String DBNAME = "gins-tonics";

    private PrintStream out = System.out;
    private PrintStream err = System.err;

    private ArangoDB arango;
    private ArangoDatabase db;
    public Gins gins;
    public Tonics tonics;
    public Garnishes garnishes;
    public Combinations combinations;
    public Users users;
    public Ratings ratings;


    public DBService(String host, String port, String user, String password){
        arango = new ArangoDB.Builder()
                .host(host)
                .port(Integer.parseInt(port))
                .user(user)
                .password(password)
                .build();
    }



    public void init(){
        loadDB();
        gins = new Gins(db, this);
        tonics = new Tonics(db, this);
        garnishes = new Garnishes(db, this);
        combinations = new Combinations(db, this);
        users = new Users(db, this);
        ratings = new Ratings(db, this);

    }

    private void loadDB(){
        try{
            arango.db(DBNAME);
        }catch(ArangoDBException e){
            //If db does not exist, fails silently
            out.println("Warning, database did not exist, created.");
            arango.createDatabase(DBNAME);

        }
        db = arango.db(DBNAME);
    }

    public void resetCollections(){
        gins.resetCollection();
        tonics.resetCollection();
        garnishes.resetCollection();
        combinations.resetCollection();
        //users.resetCollection();
        //ratings.resetCollection();
    }




}
