package project2.gintonics;

import com.arangodb.*;
import project2.gintonics.Services.Combinations;
import project2.gintonics.Services.Garnishes;
import project2.gintonics.Services.Gins;
import project2.gintonics.Services.Tonics;

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
        gins = new Gins(db);
        tonics = new Tonics(db);
        garnishes = new Garnishes(db);
        combinations = new Combinations(db);

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
    }




}
