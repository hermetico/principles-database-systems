import org.junit.Before;
import org.junit.Test;
import project2.gintonics.DB;
import project2.gintonics.DBServices;
import project2.gintonics.Entities.*;
import project2.gintonics.IDBServices;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class GeneralDBServicesTests {
    private final String HOST = "localhost";
    private final String PORT = "8529";
    private final String USER = "root";
    private final String PASSWORD = "1234";
    private final String GINTONICS_FILE = "gintonics.csv";
    private IDBServices dbServices;
    private int numCombinations = 0;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    @Before
    public void setUp() throws Exception {
        dbServices = new DBServices(HOST, PORT, USER, PASSWORD);
        dbServices.resetCollections();
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

            if(!dbServices.existsGinByName(gin.getName())){
                dbServices.insertGin(gin);
            }

            if(!dbServices.existsTonicByName(tonic.getName())){
                dbServices.insertTonic(tonic);
            }

            if(parts.length > 4) {
                garnish = new Garnish(parts[3]);
                if(!dbServices.existsGarnishByName(garnish.getName())){
                    dbServices.insertGarnish(garnish);
                }
            }


            if(garnish == null){
                combination = new Combination(gin, tonic);
            }else{
                combination = new Combination(gin, tonic, garnish);
            }

            dbServices.insertCombination(combination);
        }
        scanner.close();

    }

    @Test
    public void createUser() {
        System.out.println("Creating user Juan");
        User user = new User("Juan");
        System.out.println("Inserting it into DB");
        dbServices.insertUser(user);
        System.out.println(user.prettyPrint());

    }

    @Test
    public void createRatingAndMicroRating(){
        System.out.println("Creating rating");

        Combination combi = dbServices.getAllCombinations().get(0);
        if(dbServices.getNumUsers() == 0) createUser();
        User user = dbServices.getAllUsers().get(0);
        String comment = "Really tasty";
        int value = 9;


        Rating rating = dbServices.rateCombination(combi, user, comment, value);
        System.out.println(rating.prettyPrint());
        System.out.println(user.prettyPrint());
        System.out.println(combi.prettyPrint());

    }

    @Test
    public void createTwoRatingsAndMicroRating(){
        out.println("Creating Two ratings");

        Combination combi = dbServices.getAllCombinations().get(0);
        if(dbServices.getNumUsers() == 0) createUser();
        User user = dbServices.getAllUsers().get(0);
        String comment = "Really tasty";
        int value = 9;

        Rating rating = dbServices.rateCombination(combi, user, comment, value);
        out.println(rating.prettyPrint());
        comment = "This time it felt different";
        value = 6;
        rating = dbServices.rateCombination(combi, user, comment, value);
        out.println(rating.prettyPrint());
        out.println(user.prettyPrint());
        out.println(combi.prettyPrint());

        out.println("Checking that number of microratings and averages match expected values");
        assertEquals(user.getNumRatings(), 2);
        assertEquals((long) combi.getAvgRating(), (9+6) / 2);

    }

}
