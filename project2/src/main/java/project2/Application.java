package project2;

import project2.gintonics.Connector;
import project2.gintonics.DBServices;
import project2.gintonics.Entities.*;
import project2.gintonics.IDBServices;

import java.io.*;
import java.util.Scanner;

public class Application {
    private final String HOST = "localhost";
    private final String PORT = "8529";
    private final String USER = "root";
    private final String PASSWORD = "1234";


    private PrintStream out = System.out;
    private PrintStream err = System.err;

    private Connector service;
    private IDBServices dbservice;

    public void runApplication(){
        service = new Connector(HOST, PORT, USER, PASSWORD);
        service.connect();
        dbservice = new DBServices(service);
        inputLoop();
    }

    public void inputLoop(){
        Scanner in = new Scanner(System.in);
        Scanner in2 = new Scanner(System.in);

        int a;
        String b;
        int c;
        out.println("Welcome to Gin and Tonic ultimate menu (alpha version)");
        out.println("Please select what you want to do, by inputting the number corresponding to your choice");
        out.println("1. Create new Gin, Tonic or Garnish");
        out.println("2. Search for Gin, Tonic or Garnish");
        out.println("3. Search for a combination (Not implemented)");
        out.println("4. List combinations I have rated (Not implemented)");
        if(in.hasNextInt()) {
            a = in.nextInt();

            System.out.println("---------------------------------------------");
            switch(a){
                case 1:
                    System.out.println("You have selected to create a new entry.");
                    System.out.println("Please select what you want to create:");
                    System.out.println("1. Gin");
                    System.out.println("2. Tonic");
                    System.out.println("3. Garnish");
                    System.out.println("4. Return to main menu");
                    c = in.nextInt();
                    switch(c){
                        case 1:
                            System.out.println("You have selected to create new Gin.");
                            System.out.println("Please input it's name:");
                            String name = in2.nextLine();
                            if(!service.gins.existsByName(name))
                            {
                                Gin gin = new Gin(name);
                                service.gins.insert(gin);
                            }
                            else
                            {
                                System.out.println("Gin " + name + " already exists!");
                            }
                            break;

                        case 2:
                            System.out.println("You have selected to create new Tonic");
                            System.out.println("Please input it's name:");
                            name = in2.nextLine();

                            if(!service.tonics.existsByName(name))
                            {
                                Tonic tonic = new Tonic(name);
                                service.tonics.insert(tonic);
                            }
                            else
                            {
                                System.out.println("This tonic already exists!");
                            }
                            break;
                        case 3:
                            System.out.println("You have selected to create new Garnish");
                            System.out.println("Please input it's name:");
                            name = in2.nextLine();
                            if(!service.garnishes.existsByName(name))
                            {
                                Garnish garnish = new Garnish(name);
                                service.garnishes.insert(garnish);
                            }
                            else
                            {
                                System.out.println("This Garnish already exists!");
                            }
                        case 4:
                            //Return to main menu
                            break;
                    }
                    break;
                case 2:
                    System.out.println("You have selected the search option.");
                    System.out.println("What do you want to search for?");
                    System.out.println("Please select:");
                    System.out.println("1. Gin");
                    System.out.println("2. Tonic");
                    System.out.println("3. Garnish");
                    a = in.nextInt();
                    String name;
                    switch(a){
                        case 1:
                            System.out.println("You have selected to search for Gin");
                            System.out.println("Please input it's name:");
                            name = in2.nextLine();
                            if(!service.gins.existsByName(name))
                            {
                                System.out.println("Gin " + name + " exists in the database.");
                            }
                            else
                            {
                                System.out.println("Gin " + name + " is not found in the database.");
                            }
                        case 2:
                            System.out.println("You have selected to search for Tonic");
                            System.out.println("Please input it's name:");
                            name = in2.nextLine();
                            if(!service.tonics.existsByName(name))
                            {
                                System.out.println("Tonic " + name + " exists in the database.");
                            }
                            else
                            {
                                System.out.println("Tonic " + name + " is not found in the database.");
                            }
                        case 3:
                            System.out.println("You have selected to search for Garnish");
                            System.out.println("Please input it's name:");
                            name = in2.nextLine();
                            if(!service.garnishes.existsByName(name))
                            {
                                System.out.println("Garnish " + name + " exists in the database.");
                            }
                            else
                            {
                                System.out.println("Garnish " + name + " is not found in the database.");
                            }
                    }
                    break;
                case 3:
                    System.out.println("You have selected to search for Gin-Tonic-Garnish combination.");
                    System.out.println("Please input name of Gin:");
                    String gin = in2.nextLine();
                    if(!service.gins.existsByName(gin))
                    {
                        System.out.println("Gin " + gin + " does not exist!");
                        break;
                    }
                    System.out.println("Please input name of Tonic:");
                    String tonic = in2.nextLine();
                    if(!service.tonics.existsByName(tonic))
                    {
                        System.out.println("Tonic " + tonic + " does not exist!");
                        break;
                    }
                    System.out.println("Please input name of Garnish (Optional):");
                    String garnish = in2.nextLine();
                    boolean garnishExists = true;
                    if(!service.garnishes.existsByName(garnish))
                    {
                        System.out.println("Garnish " + garnish + " does not exist!");
                        garnishExists = false;
                    }
                    if(garnishExists)
                    {
                        System.out.println(dbservice.getRatingsByGinAndTonicAndGarnish(gin, tonic, garnish));
                    }
                    else
                    {
                        //alexCombo(rating ,dbservice.getRatingsByGinAndTonic(gin, tonic))
                        System.out.println(dbservice.getRatingsByGinAndTonic(gin, tonic).toString());
                        int numRatings = dbservice.getNumRatings();

                        //double averageRating = dbservice.
                        //System.out.println(dbservice.)
                    }
                    System.out.println("Would you like to rate this combination?");
                    String input = in2.nextLine();


                    break;
                case 4:
                    System.out.println("You have selected to display all ratings created by you.");
                    //if there are any - display them.
            }

        }
        else
        {
            System.out.println("Please input a valid integer");
        }



    }


}
