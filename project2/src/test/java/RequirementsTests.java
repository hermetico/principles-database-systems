import org.junit.Before;
import org.junit.Test;
import project2.gintonics.DBServices;
import project2.gintonics.Entities.*;
import project2.gintonics.IDBServices;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class RequirementsTests {
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

            if(parts.length > 3){
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

            if(!dbServices.existsCombination(combination)){
                dbServices.insertCombination(combination);
            }

        }
        scanner.close();

    }


    @Test
    public void allTests(){
        out.println("Creating user Juan");
        createUser("Juan");
        out.println("Creating user Alexander");
        createUser("Alexander");

        out.println("Creating random ratings for Juan and Alexander");
        createManyRatings(25, "Juan");
        createManyRatings(75, "Alexander");

        String gin, tonic;
        gin = "Old Raj";
        tonic = "Fenitmans";
        out.println("Querying ratings for combinations that contain gin: " + gin + ", tonic: " + tonic);
        out.println("This should contains all the possible combinations that contain this gin and tonic and whatever garnish");
        out.println("but also the unique combination of this gin and tonic and no garnish, if it exists...");
        queryRatingByGinAndTonic(gin, tonic, 10);

        gin = "Millers";
        String garnish = "Lime";
        out.println("\nNow, the same with a garnish. This combination should be unique. Gin: " + gin + ", tonic: " + tonic + ", garnish: " + garnish);
        queryRatingByGinAndTonicAndGarnish(gin, tonic, garnish, 10);

        out.println("\nShowing the ratings of one user in two different modes, first by microrating, which are the important values of a rating");
        out.println("This microratings are contained inside the user document, so we only need to query the user");
        queryMicroRatingsByUserName("Juan");

        out.println("\nNow we are going to query the full rating");
        queryFullRatingsByUserName("Juan");

        out.println("\nSetting some ratings as helpful and showing 10 top most helpful ratings");
        setRatingsAsHelpful(350);
        showRatingsByHelpfulness(10);

    }

    public void createUser(String name){
        User user = new User(name);
        dbServices.insertUser(user);
        System.out.println("User: " + user.prettyPrint());
    }

    public void createManyRatings(int count, String userName){
        Random rn = new Random();
        out.println("Creating " + count + " ratings for " + userName);

        // we need to decide if names are unique, so far they are not
        User user = dbServices.getUsersByName(userName).get(0);

        List<Combination> combinations = dbServices.getAllCombinations();
        int numCombinations = combinations.size();

        for(int i = 0; i < count; i++){
            Combination combi = combinations.get(i % numCombinations);
            dbServices.rateCombination(combi, user, "Good enough", rn.nextInt(11));

        }
    }

    public void queryRatingByGinAndTonic(String gin, String tonic, int pageSize){
        int pages = (int) Math.ceil(dbServices.getCountOfRatingsByGinAndTonic(gin, tonic) / pageSize) + 1;
        for(int i = 0; i < pages; i++){
            List<CombinationRatingQuery> combinationRatings = dbServices.getRatingsByGinAndTonic(gin, tonic, i, pageSize);
            out.println("\nShowing ratings for page: " + (i+1));
            for(CombinationRatingQuery crq : combinationRatings){
                out.println(crq.getCombination().prettyPrint());
                out.println(crq.getRating().prettyPrint());
                out.println("---");
            }
        }
    }

    public void queryRatingByGinAndTonicAndGarnish(String gin, String tonic, String garnish,  int pageSize){
        int pages = (int) Math.ceil(dbServices.getCountOfRatingsByGinAndTonicAndGarnish(gin, tonic, garnish) / pageSize) + 1;
        boolean showCombination  = true;
        for(int i = 0; i < pages; i++){
            List<CombinationRatingQuery> combinationRatings = dbServices.getRatingsByGinAndTonicAndGarnish(gin, tonic, garnish,  i, pageSize);

            out.println("\nShowing ratings for page: " + (i+1));
            for(CombinationRatingQuery crq : combinationRatings){
                if( showCombination ){
                    showCombination = false;
                    // The combination is unique, we can just show it in the first page
                    out.println(crq.getCombination().prettyPrint());
                }

                out.println(crq.getRating().prettyPrint());
                out.println("---");
            }
        }
    }

    public void queryMicroRatingsByUserName(String userName){
        out.println("Querying user: " + userName);
        User user = dbServices.getUsersByName(userName).get(0);

        out.println("User: " + user.getName());
        out.println("Combinations rated: " + user.getNumRatings());
        for(MicroRating mr: user.getMicroRatings()){
            out.println("\tCombination: " + mr.getName());
            out.println("\tRating: " + mr.getRatingValue());
        }
    }

    public void queryFullRatingsByUserName(String userName){
        out.println("Querying user: " + userName);
        int pageSize = 10;
        int pages = (int) Math.ceil(dbServices.getCountOfRatingsByUserName(userName) / pageSize);
        for(int i = 0; i < pages; i++) {
            List<Rating> ratings = dbServices.getRatingsByUserName(userName, i, pageSize);
            for (Rating rating : ratings) {
                out.println(rating.prettyPrint());
            }
        }
    }

    public void setRatingsAsHelpful(int count){
        Random rn = new Random();
        int numRatings = dbServices.getNumRatings();
        List<Rating> ratings = dbServices.getAllRatings();
        for(int i = 0; i < count; i++) {
            dbServices.markRatingAsHelpful(ratings.get(rn.nextInt(numRatings)));
        }
    }

    public void showRatingsByHelpfulness(int count){
        List<Rating> ratings = dbServices.getRatingsSortedByHelpfulness(0, count);
        for (Rating rating : ratings) {
            out.println(rating.prettyPrint());
        }

    }
}
