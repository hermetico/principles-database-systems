package project2;

import project2.gintonics.DBService;
import java.io.*;

public class Application {
    private final String HOST = "localhost";
    private final String PORT = "8529";
    private final String USER = "root";
    private final String PASSWORD = "1234";


    private PrintStream out = System.out;
    private PrintStream err = System.err;

    private DBService service;

    public void runApplication(){
        service = new DBService(HOST, PORT, USER, PASSWORD);
        service.init();
        inputLoop();
    }

    public void inputLoop(){

        out.println("Nothing here yet");
    }


}
