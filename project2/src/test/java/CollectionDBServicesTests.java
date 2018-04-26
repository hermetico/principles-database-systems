
import org.junit.Before;
import org.junit.Test;

import project2.gintonics.Entities.*;
import project2.gintonics.DB;

import java.io.File;

import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CollectionDBServicesTests {
    private final String HOST = "localhost";
    private final String PORT = "8529";
    private final String USER = "root";
    private final String PASSWORD = "1234";
    private final String GINTONICS_FILE = "gintonics.csv";
    DB db;
    int numCombinations = 0;


    @Before
    public void setUp() throws Exception {
        db = new DB(HOST, PORT, USER, PASSWORD);
        db.init();
        db.resetCollections();
        fillDB();

    }

    public void fillDB() throws Exception {

        Combination combination;
        Gin gin;
        Tonic tonic;
        Garnish garnish;



        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(GINTONICS_FILE).getFile());
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            numCombinations++;
            gin = null; tonic = null; garnish = null;
            String line = scanner.nextLine();
            String[] parts = line.split(",");


            gin = new Gin(parts[0]);
            tonic = new Tonic(parts[2]);




            if(!db.gins.existsByName(gin.getName())){
                db.gins.insert(gin);
            }

            if(!db.tonics.existsByName(tonic.getName())){
                db.tonics.insert(tonic);
            }

            if(parts.length > 4) {
                garnish = new Garnish(parts[3]);
                if(!db.garnishes.existsByName(garnish.getName())){
                    db.garnishes.insert(garnish);
                }
            }


            if(garnish == null){
                combination = new Combination(gin, tonic);
            }else{
                combination = new Combination(gin, tonic, garnish);
            }

            db.combinations.insert(combination);
        }
        scanner.close();

    }
    @Test
    public void printAndCountCombinations(){
        System.out.println("Printing all combinations");
        List<Combination> combinations = db.combinations.getAll();
        for(Combination combination: combinations){
            System.out.println(combination.prettyPrint());
        }
        System.out.println("Testing num of current combinations");
        int size = db.combinations.getSize();
        assertEquals(numCombinations,size);
        System.out.println("Num of combinations = " + size + ", as expected");
    }

    @Test
    public void getCombination(){
        List<Combination> combinations = db.combinations.getAll();
        Combination combi = combinations.get(7);

        System.out.println("Selecting combination with key: " + combi.getKey());
        combi = db.combinations.getByKey(combi.getKey());
        assertNotNull(combi);
        System.out.println(combi.prettyPrint());

        System.out.println("Selecting combination with key 'Wrong-key' which does not exist");
        combi = db.combinations.getByKey("Wrong-key");
        assertNull(combi);
        System.out.println("It does not exist");

    }

    @Test
    public void deleteCombination(){
        List<Combination> combinations = db.combinations.getAll();
        Combination combi = combinations.get(7);
        System.out.println("Deleting combination with key: " + combi.getKey());
        db.combinations.deleteByKey(combi.getKey());
        System.out.println("Selecting combination with key " + combi.getKey() + ", which should not exist anymore");
        combi = db.combinations.getByKey(combi.getKey());
        assertNull(combi);
        System.out.println("It does not exist");


    }

    @Test
    public void deleteGin(){
        System.out.println("Deleting a gin");
        List<Gin> all = db.gins.getAll();
        int total = all.size();
        db.gins.delete(all.get(0).getKey());
        assertEquals(db.gins.getSize(), total - 1);

    }

    @Test
    public void deleteTonic() {
        System.out.println("Deleting a tonic");
        List<Tonic> all = db.tonics.getAll();
        int total = all.size();
        db.tonics.delete(all.get(0).getKey());
        assertEquals(db.tonics.getSize(), total - 1);
    }

    @Test
    public void deleteGarnish() {
        System.out.println("Deleting a garnish");
        List<Garnish> all = db.garnishes.getAll();
        int total = all.size();
        db.garnishes.delete(all.get(0).getKey());
        assertEquals(db.garnishes.getAll().size(), total - 1);
    }


    @Test
    public void createUser() {
        System.out.println("Creating user Juan");
        User user = new User("Juan");
        System.out.println("Inserting it into DB");
        db.users.insert(user);
        System.out.println(user.prettyPrint());

    }

    @Test
    public void createRatingAndMicroRating(){
        System.out.println("Creating rating");
        Combination combi = db.combinations.getAll().get(0);
        if(db.users.getSize() == 0) createUser();
        User user = db.users.getAll().get(0);
        String comment = "Really tasty";
        int value = 9;
        Rating rating = new Rating(combi, user, comment, value);
        // append also the user to update it
        db.ratings.insert(rating);
        System.out.println(rating.prettyPrint());

        user = db.users.getByKey(user.getKey());
        System.out.println(user.prettyPrint());
    }
}
