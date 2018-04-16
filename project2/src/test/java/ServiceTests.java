
import com.arangodb.entity.BaseDocument;
import org.junit.Before;
import org.junit.Test;

import project2.gintonics.Entities.Combination;
import project2.gintonics.DBService;
import project2.gintonics.Entities.Gin;

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

            Combination combination = new Combination(parts[0], parts[2], parts[3]);
            combination.setKey(parts[4]);
            service.combinations.insert(combination);
        }
        scanner.close();

    }

    @Test
    public void testNumCombinations(){
        System.out.println("Testing num of current combinations");
        List<BaseDocument> documents = service.combinations.getAll();
        System.out.println(documents.size());
        assertEquals(numCombinations,documents.size());
        System.out.println("Num of combinations = " + documents.size() + ", as expected");
    }

    @Test
    public void getCombination(){
        System.out.println("Selecting combination num 37");
        Combination combi = service.combinations.getByKey("37");
        assertNotNull(combi);
        System.out.println(combi);

        System.out.println("Selecting combination num 999 which does not exist");
        combi = service.combinations.getByKey("99");
        assertNull(combi);
        System.out.println("It does not exist");

    }

    @Test
    public void deleteCombination(){
        System.out.println("Deleting combination num 37");
        service.combinations.deleteByKey("37");
        System.out.println("Selecting combination num 37, which should not exist anymore");
        Combination combi = service.combinations.getByKey("37");
        assertNull(combi);
        System.out.println("It does not exist");


    }
}
