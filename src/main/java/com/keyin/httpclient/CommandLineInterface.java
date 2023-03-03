package com.keyin.httpclient;

import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/*
     This command line interface uses a simple implementation of the Command Pattern.

     --> The Command Pattern is a behavioral design pattern in which an object is used to
          represent and encapsulate all the information needed to call a method at a later time.

     --> This pattern is used to decouple the object that invokes the operation from the one
          that knows how to perform it.
*/

/**
 *      This command line interface uses a simple implementation of the Command Pattern.

 *      --> The Command Pattern is a behavioral design pattern in which an object is used to
 *           represent and encapsulate all the information needed to call a method at a later time.

 *      --> This pattern is used to decouple the object that invokes the operation from the one
 *           that knows how to perform it.
 */
public class CommandLineInterface {
    HTTPClient httpClient = new HTTPClient();

    private Scanner scanner = new Scanner(System.in);

    // --------------------------------------------- (Main Menu) --------------------------------------------

    private Map<Integer, Command> commands = new HashMap<>();

    public CommandLineInterface() {
        // Initialize commands map
        commands.put(1, new QuestionsCommand()); // Sprint Questions
        commands.put(2, new QueryCommand()); // Query
    }


    // Starts the command line interface.
    public void start() {
        System.out.println("---------------------------------------");
        System.out.println("\nWelcome to the HTTP Client Application!\n        (To exit, enter 99.)");
        while (true) {
            displayMainMenu();
            int choice = readIntInput();
            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else if (choice == 99) {
                scanner.close();
                System.out.println("\nGoodbye!");
                System.exit(0); // Exit program
            } else {
                System.out.println("\nInvalid choice.");
            }
        }
    }


    // Displays the main menu.
    private void displayMainMenu() {
        System.out.println("\n-------------- MAIN MENU --------------");
        System.out.println("1. Questions");
        System.out.println("2. Query Database");
        System.out.print("> ");
    }


    // Reads and validates user input.
    private int readIntInput() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();

            System.out.print("\nInvalid input. Please enter a number.\n");
            System.out.print("> ");
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume remaining newline character
        return input;
    }

    // Declares a method for executing a command.
    private interface Command {
        void execute();
    }

    // ------------------------------- Command 1 (Main Menu --> 1. Questions) -------------------------------

    private class QuestionsCommand implements Command {
        private Map<Integer, SubCommand> subCommands = new HashMap<>();

        private interface SubCommand {
            void execute();
        }

        public QuestionsCommand() {
            subCommands.put(1, new Question1SubCommand());
            subCommands.put(2, new Question2SubCommand());
            subCommands.put(3, new Question3SubCommand());
            subCommands.put(4, new Question4SubCommand());
        }

        @Override
        public void execute() {
            while (true) {
                displayQuestionsCommandMenu();
                int choice = readIntInput();
                SubCommand subCommand = subCommands.get(choice);
                if (subCommand != null) {
                    subCommand.execute();
                } else if (choice == 0) { // Go back to previous menu
                    return; // exit sub-menu
                } else if (choice == 99) {
                    scanner.close();
                    System.out.println("\nGoodbye!");
                    System.exit(0); // Exit program
                } else {
                    System.out.println("\nInvalid choice.");
                }
            }
        }

        private void displayQuestionsCommandMenu() {
            System.out.println("\n-------------- QUESTIONS --------------");
            System.out.println("1. What airports are in what cities?");
            System.out.println("2. List all aircraft passengers have travelled on?");
            System.out.println("3. Which airports can aircraft take off from and land at?");
            System.out.println("4. What airports have passengers used?");
            System.out.println("0. Back");
            System.out.print("> ");
        }

        // (Questions --> 1. What airports are in what cities?)
        private class Question1SubCommand implements SubCommand {
            @Override
            public void execute() {
                    try{
                        httpClient.runTask("http://localhost:8080/cities_airports/");
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
            }

        }

        // (Questions --> 2. List all aircraft passengers have travelled on?)
        private class Question2SubCommand implements SubCommand {
            @Override
            public void execute() {
                    try{
                        httpClient.runTask("http://localhost:8080/aircraft_passengers/");
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
            }
        }

        // (Questions --> 3. Which airports can aircraft take off from and land at?)
        private class Question3SubCommand implements SubCommand {
            @Override
            public void execute() {
                    try{
                        httpClient.runTask("http://localhost:8080/aircraft_airports/");
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
            }
        }

        // (Questions --> 4. What airports have passengers used?)
        private class Question4SubCommand implements SubCommand {
            @Override
            public void execute() {
                    try{
                        httpClient.runTask("http://localhost:8080/airports_passengers/");
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
            }
        }
    }

    // --------------------------------- Command 2 (Main Menu --> 2. Query) ---------------------------------
    private class QueryCommand implements Command {

        private Map<Integer, SubCommand> subCommands = new HashMap<>();

        private interface SubCommand {
            void execute();
        }

        private interface SubSubCommand {
            void execute();
        }

        public QueryCommand() {
            subCommands.put(1, new GetAllSubCommand());
            subCommands.put(2, new GetByIdSubCommand());
        }

        @Override
        public void execute() {
            while (true) {
                displayQueryCommandMenu();
                int choice = readIntInput();
                SubCommand subCommand = subCommands.get(choice);
                if (subCommand != null) {
                    subCommand.execute();
                } else if (choice == 0) {
                    return;
                } else if (choice == 99) {
                    scanner.close();
                    System.out.println("\nGoodbye!");
                    System.exit(0); // Exit program
                } else {
                    System.out.println("\nInvalid choice.");
                }
            }
        }

        private void displayQueryCommandMenu() {
            System.out.println("\n---------- SELECT QUERY TYPE ----------");
            System.out.println("1. Get All");
            System.out.println("2. Get By ID");
            System.out.println("0. Back");
            System.out.print("> ");
        }

        // ------------------------------ Sub-Command 1 (Query --> 1. Get All) ------------------------------
        private class GetAllSubCommand implements SubCommand {

            private Map<Integer, SubSubCommand> subSubCommands = new HashMap<>();

            public GetAllSubCommand() {
                subSubCommands.put(1, new GetAllCitiesSubSubCommand());
                subSubCommands.put(2, new GetAllPassengersSubSubCommand());
                subSubCommands.put(3, new GetAllAirportsSubSubCommand());
                subSubCommands.put(4, new GetAllAircraftSubSubCommand());

            }

            @Override
            public void execute() {
                while (true) {
                    displayGetAllSubCommandMenu();
                    int choice = readIntInput();
                    SubSubCommand subSubCommand = subSubCommands.get(choice);
                    if (subSubCommand != null) {
                        subSubCommand.execute();
                    } else if (choice == 0) {
                        return; // exit sub-menu
                    } else if (choice == 99) {
                        scanner.close();
                        System.out.println("\nGoodbye!");
                        System.exit(0); // Exit program
                    } else {
                        System.out.println("\nInvalid choice.");
                    }
                }
            }

            private void displayGetAllSubCommandMenu() { // Get All Menu
                System.out.println("\n-------- GET ALL: SELECT TABLE --------");
                System.out.println("1. Cities");
                System.out.println("2. Passengers");
                System.out.println("3. Airports");
                System.out.println("4. Aircraft");
                System.out.println("0. Back");
                System.out.print("> ");
            }

            // ----------- Sub-Sub-Command Execution (Query --> Get All --> <#. Sub-Sub-Command>) -----------

            // (Query --> Get All --> 1. Cities)
            private class GetAllCitiesSubSubCommand implements QueryCommand.SubSubCommand {
                @Override
                public void execute() {
//                    System.out.println("CITIES: Executing sub-sub-command 1 of sub-command 1 of command 2");
                    try{
                        httpClient.runTask("http://localhost:8080/cities/");
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
                }
            }

            // (Query --> Get All --> 2. Passengers)
            private class GetAllPassengersSubSubCommand implements QueryCommand.SubSubCommand {
                @Override
                public void execute() {
//                    System.out.println("PASSENGERS: Executing sub-sub-command 2 of sub-command 1 of command 2");
                    try{
                        httpClient.runTask("http://localhost:8080/passengers/");
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
                }
            }

            // (Query --> Get All --> 3. Airports)
            private class GetAllAirportsSubSubCommand implements QueryCommand.SubSubCommand {
                @Override
                public void execute() {
//                    System.out.println("AIRPORTS: Executing sub-sub-command 3 of sub-command 1 of command 2");
                    try{
                        httpClient.runTask("http://localhost:8080/airports/");
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
                }
            }

            // (Query --> Get All --> 4. Aircraft)
            private class GetAllAircraftSubSubCommand implements QueryCommand.SubSubCommand {
                @Override
                public void execute() {
//                    System.out.println("AIRCRAFT: Executing sub-sub-command 4 of sub-command 1 of command 2");
                    try{
                        httpClient.runTask("http://localhost:8080/aircraft/");
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
                }
            }
        }


        // ----------------------------- Sub-Command 2 (Query --> 1. Get By ID) -----------------------------

        private interface SubSubCommandWithInt {
            void execute(int id);
        }

        private class GetByIdSubCommand implements SubCommand {

            private Map<Integer, SubSubCommandWithInt> subSubCommandsWithID = new HashMap<>();

            public GetByIdSubCommand() {
                subSubCommandsWithID.put(1, new GetCityByIdSubSubCommand());
                subSubCommandsWithID.put(2, new GetPassengerByIdSubSubCommand());
                subSubCommandsWithID.put(3, new GetAirportByIdSubSubCommand());
                subSubCommandsWithID.put(4, new GetAircraftByIdSubSubCommand());
            }

            @Override
            public void execute() {
                while (true) {
                    displayGetByIdSubCommandMenu();
                    int choice = readIntInput();

                    if (choice == 0) {
                        return; // exit sub-menu
                    }
                    if (choice == 99) {
                        scanner.close();
                        System.out.println("\nGoodbye!");
                        System.exit(0); // Exit program
                    }

                    SubSubCommandWithInt subSubCommand = subSubCommandsWithID.get(choice);

                    if (subSubCommand == null) {
                        System.out.println("\nInvalid choice.");
                    } else {
                        String chosenEntity = null;
                        switch (choice) {
                            case 1 -> chosenEntity = "a City";
                            case 2 -> chosenEntity = "a Passenger";
                            case 3 -> chosenEntity = "an Airport";
                            case 4 -> chosenEntity = "an Aircraft";
                        }

                        System.out.print("\n--------------- ENTER ID --------------\n\n");
                        System.out.printf("Please enter %s ID: ", chosenEntity);

                        while (true) {
                            int id = readIntInput();
                            if (id < 0) {
                                System.out.print("\nInvalid input. Please enter a valid whole number: ");
                            } else {
                                subSubCommand.execute(id);
                                break;
                            }
                        }
                    }
                }
            }


            private void displayGetByIdSubCommandMenu() {
                System.out.println("\n----- GET BY ID: SELECT TABLE -----");
                System.out.println("1. City");
                System.out.println("2. Passenger");
                System.out.println("3. Airport");
                System.out.println("4. Aircraft");
                System.out.println("0. Back");
                System.out.print("> ");
            }


            // ---------- Sub-Sub-Command Execution (Query --> Get By ID --> <#. Sub-Sub-Command>) ----------

            // (Query --> Get By ID --> 1. City)
            private class GetCityByIdSubSubCommand implements QueryCommand.SubSubCommandWithInt {
                @Override
                public void execute(int id) {
//                    System.out.println("City: Executing sub-sub-command 1 of sub-command 2 of command 2");
                    try{
                        httpClient.runTask("http://localhost:8080/city/" + id);
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
                }
            }

            // (Query --> Get By ID --> 2. Passenger)
            private class GetPassengerByIdSubSubCommand implements QueryCommand.SubSubCommandWithInt {
                @Override
                public void execute(int id) {
//                    System.out.println("Passenger: Executing sub-sub-command 2 of sub-command 2 of command 2");
                    // TODO: Make sure passenger endpoint is added to the backend
                    try{
                        httpClient.runTask("http://localhost:8080/passenger/" + id);
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
                }
            }

            // (Query --> Get By ID --> 3. Airport)
            private class GetAirportByIdSubSubCommand implements QueryCommand.SubSubCommandWithInt {
                @Override
                public void execute(int id) {
//                    System.out.println("Airport: Executing sub-sub-command 3 of sub-command 2 of command 2");
                    try{
                        httpClient.runTask("http://localhost:8080/airport/" + id);
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
                }
            }

            // (Query --> Get By ID --> 4. Aircraft)
            private class GetAircraftByIdSubSubCommand implements QueryCommand.SubSubCommandWithInt {
                @Override
                public void execute(int id) {
//                    System.out.println("Aircraft: Executing sub-sub-command 4 of sub-command 2 of command 2");
                    try{
                        httpClient.runTask("http://localhost:8080/aircraft/" + id);
                    }catch(Exception e){
                        System.out.println("    ↪ Error: " + e + "\n");
                    }
                }
            }
        }
    }


    // Client
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ParseException {
        CommandLineInterface cli = new CommandLineInterface();
        cli.start();
    }
}

