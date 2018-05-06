
import org.junit.Before;
import org.junit.Test;

import project2.gintonics.Connector;
import project2.gintonics.Entities.*;

import java.io.File;

import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CollectionConnectorServicesTests {
    private final String HOST = "localhost";
    private final String PORT = "8529";
    private final String USER = "root";
    private final String PASSWORD = "1234";
    private final String GINTONICS_FILE = "gintonics.csv";
    Connector connector;
    int numCombinations = 0;


    @Before
    public void setUp() throws Exception {
        connector = new Connector(HOST, PORT, USER, PASSWORD);
        connector.connect();
        connector.resetAllCollections();
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




            if(!connector.gins.existsByName(gin.getName())){
                connector.gins.insert(gin);
            }

            if(!connector.tonics.existsByName(tonic.getName())){
                connector.tonics.insert(tonic);
            }

            if(parts.length > 4) {
                garnish = new Garnish(parts[3]);
                if(!connector.garnishes.existsByName(garnish.getName())){
                    connector.garnishes.insert(garnish);
                }
            }


            if(garnish == null){
                combination = new Combination(gin, tonic);
            }else{
                combination = new Combination(gin, tonic, garnish);
            }

            connector.combinations.insert(combination);
        }
        scanner.close();

    }

    @Test
    public void printAndCountCombinations(){
        System.out.println("Printing all combinations");
        List<Combination> combinations = connector.combinations.getAll();
        for(Combination combination: combinations){
            System.out.println(combination.prettyPrint());
        }
        System.out.println("Testing num of current combinations");
        int size = connector.combinations.getSize();
        assertEquals(numCombinations,size);
        System.out.println("Num of combinations = " + size + ", as expected");
    }

    @Test
    public void getCombination(){
        List<Combination> combinations = connector.combinations.getAll();
        Combination combi = combinations.get(7);

        System.out.println("Selecting combination with key: " + combi.getKey());
        combi = connector.combinations.getByKey(combi.getKey());
        assertNotNull(combi);
        System.out.println(combi.prettyPrint());

        System.out.println("Selecting combination with key 'Wrong-key' which does not exist");
        combi = connector.combinations.getByKey("Wrong-key");
        assertNull(combi);
        System.out.println("It does not exist");

    }

    @Test
    public void deleteCombination(){
        List<Combination> combinations = connector.combinations.getAll();
        Combination combi = combinations.get(7);
        System.out.println("Deleting combination with key: " + combi.getKey());
        connector.combinations.deleteByKey(combi.getKey());
        System.out.println("Selecting combination with key " + combi.getKey() + ", which should not exist anymore");
        combi = connector.combinations.getByKey(combi.getKey());
        assertNull(combi);
        System.out.println("It does not exist");


    }

    @Test
    public void deleteGin(){
        System.out.println("Deleting a gin");
        List<Gin> all = connector.gins.getAll();
        int total = all.size();
        connector.gins.delete(all.get(0).getKey());
        assertEquals(connector.gins.getSize(), total - 1);

    }

    @Test
    public void deleteTonic() {
        System.out.println("Deleting a tonic");
        List<Tonic> all = connector.tonics.getAll();
        int total = all.size();
        connector.tonics.delete(all.get(0).getKey());
        assertEquals(connector.tonics.getSize(), total - 1);
    }

    @Test
    public void deleteGarnish() {
        System.out.println("Deleting a garnish");
        List<Garnish> all = connector.garnishes.getAll();
        int total = all.size();
        connector.garnishes.delete(all.get(0).getKey());
        assertEquals(connector.garnishes.getAll().size(), total - 1);
    }

    @Test
    public void createUser() {
        System.out.println("Creating user Juan");
        User user = new User("Juan");
        System.out.println("Inserting it into Connector");
        connector.users.insert(user);
        System.out.println(user.prettyPrint());

    }

    @Test
    public void createRatingAndMicroRating(){
        System.out.println("Creating rating");
        Combination combi = connector.combinations.getAll().get(0);
        if(connector.users.getSize() == 0) createUser();
        User user = connector.users.getAll().get(0);
        String comment = "Really tasty";
        int value = 9;
        Rating rating = new Rating(combi, user, comment, value);
        // append also the user to update it
        connector.ratings.insert(rating);
        System.out.println(rating.prettyPrint());

        user = connector.users.getByKey(user.getKey());
        System.out.println(user.prettyPrint());
    }
}
