
import com.arangodb.entity.BaseDocument;
import org.junit.Before;
import org.junit.Test;

import project2.gintonics.Entities.Combination;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Garnish;
import project2.gintonics.Entities.Gin;
import project2.gintonics.Entities.Tonic;

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

            if(!service.gins.exists(gin)){
                service.gins.insert(gin);
            }
            if(!service.tonics.exists(tonic)){
                service.tonics.insert(tonic);
            }

            if(!service.garnishes.exists(garnish)){
                service.garnishes.insert(garnish);
            }
            Combination combination = new Combination(gin, tonic, garnish);
            //combination.setKey(parts[4]);
            service.combinations.insert(combination);
        }
        scanner.close();

    }

    @Test
    public void testNumCombinations(){
        System.out.println("Testing num of current combinations");
        List<BaseDocument> documents = service.combinations.getAll();
        assertEquals(numCombinations,documents.size());
        System.out.println("Num of combinations = " + documents.size() + ", as expected");
    }

    @Test
    public void getCombination(){
        List<BaseDocument> combinations = service.combinations.getAll();
        Combination combi = new Combination(combinations.get(7));

        System.out.println("Selecting combination with key: " + combi.getKey());
        combi = service.combinations.getByKey(combi.getKey());
        assertNotNull(combi);
        System.out.println(combi.toString());

        System.out.println("Selecting combination with key 'Wrong-key' which does not exist");
        combi = service.combinations.getByKey("Wrong-key");
        assertNull(combi);
        System.out.println("It does not exist");

    }

    @Test
    public void deleteCombination(){
        List<BaseDocument> combinations = service.combinations.getAll();
        Combination combi = new Combination(combinations.get(7));
        System.out.println("Deleting combination with key: " + combi.getKey());
        service.combinations.deleteByKey(combi.getKey());
        System.out.println("Selecting combination with key " + combi.getKey() + ", which should not exist anymore");
        combi = service.combinations.getByKey(combi.getKey());
        assertNull(combi);
        System.out.println("It does not exist");


    }
}
