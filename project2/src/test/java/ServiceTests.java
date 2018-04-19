
import org.junit.Before;
import org.junit.Test;

import project2.gintonics.Entities.*;
import project2.gintonics.DBService;

import java.io.File;

import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ServiceTests {
    private final String HOST = "localhost";
    private final String PORT = "8529";
    private final String USER = "root";
    private final String PASSWORD = "1234";
    private final String GINTONICS_FILE = "gintonics.csv";
    DBService service;
    int numCombinations = 44;

    @Before
    public void setUp() throws Exception {
        service  = new DBService(HOST, PORT, USER, PASSWORD);
        service.init();
        service.resetCollections();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(GINTONICS_FILE).getFile());
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            Gin gin = new Gin(parts[0]);
            Tonic tonic = new Tonic(parts[2]);
            Garnish garnish = new Garnish(parts[3]);


            if(!service.gins.existsByName(gin.getName())){
                service.gins.insert(gin);
            }

            if(!service.tonics.existsByName(tonic.getName())){
                service.tonics.insert(tonic);
            }

            if(!service.garnishes.existsByName(garnish.getName())){
                service.garnishes.insert(garnish);
            }

            Combination combination = new Combination(gin, tonic, garnish);
            service.combinations.insert(combination);
        }
        scanner.close();

    }

    @Test
    public void testNumCombinations(){
        System.out.println("Testing num of current combinations");
        int size = service.combinations.getSize();
        assertEquals(numCombinations,size);
        System.out.println("Num of combinations = " + size + ", as expected");
    }

    @Test
    public void getCombination(){
        List<Combination> combinations = service.combinations.getAll();
        Combination combi = combinations.get(7);

        System.out.println("Selecting combination with key: " + combi.getKey());
        combi = service.combinations.getByKey(combi.getKey());
        assertNotNull(combi);
        System.out.println(combi.prettyPrint());

        System.out.println("Selecting combination with key 'Wrong-key' which does not exist");
        combi = service.combinations.getByKey("Wrong-key");
        assertNull(combi);
        System.out.println("It does not exist");

    }

    @Test
    public void deleteCombination(){
        List<Combination> combinations = service.combinations.getAll();
        Combination combi = combinations.get(7);
        System.out.println("Deleting combination with key: " + combi.getKey());
        service.combinations.deleteByKey(combi.getKey());
        System.out.println("Selecting combination with key " + combi.getKey() + ", which should not exist anymore");
        combi = service.combinations.getByKey(combi.getKey());
        assertNull(combi);
        System.out.println("It does not exist");


    }

    @Test
    public void deleteGin(){
        System.out.println("Deleting a gin");
        List<Gin> all = service.gins.getAll();
        int total = all.size();
        service.gins.delete(all.get(0).getKey());
        assertEquals(service.gins.getSize(), total - 1);

    }

    @Test
    public void deleteTonic() {
        System.out.println("Deleting a tonic");
        List<Tonic> all = service.tonics.getAll();
        int total = all.size();
        service.tonics.delete(all.get(0).getKey());
        assertEquals(service.tonics.getSize(), total - 1);
    }

    @Test
    public void deleteGarnish() {
        System.out.println("Deleting a garnish");
        List<Garnish> all = service.garnishes.getAll();
        int total = all.size();
        service.garnishes.delete(all.get(0).getKey());
        assertEquals(service.garnishes.getAll().size(), total - 1);
    }


    @Test
    public void createUser() {
        System.out.println("Creating user Juan");
        User user = new User("Juan");
        System.out.println("Inserting it into DB");
        service.users.insert(user);
        System.out.println(user.prettyPrint());

    }

    @Test
    public void createRatingAndMicroRating(){
        System.out.println("Creating rating");
        Combination combi = service.combinations.getAll().get(0);
        if(service.users.getSize() == 0) createUser();
        User user = service.users.getAll().get(0);
        String comment = "Really tasty";
        int value = 9;
        Rating rating = new Rating(combi, user, comment, value);
        // append also the user to update it
        service.ratings.insert(rating);
        System.out.println(rating.prettyPrint());

        user = service.users.getByKey(user.getKey());
        System.out.println(user.prettyPrint());
    }
}
