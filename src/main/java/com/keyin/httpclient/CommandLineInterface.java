package com.keyin.httpclient;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

// HTTP Client Imports

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
     This command line interface uses a simple implementation of the Command Pattern.
     ---- The Command Pattern is a behavioral design pattern in which an object is used to
          represent and encapsulate all the information needed to call a method at a later time.
     ---- This pattern is used to decouple the object that invokes the operation from the one
          that knows how to perform it.
*/

// TODO: (?) Add exit option to all sub-menus
// TODO: (?) See if Kara/Glen feel okay with me changing /airport/passengers_search so that it searches by both
//           first & last name instead of just last name. Perhaps the firstName could be optional?
//           (Eg. "/airport/passengers_search?firstName=Jane&lastName=Doe")
// TODO: DON'T FORGET TO CLOSE SCANNER (scanner.close())
// TODO: Javadoc

public class CommandLineInterface {
    HTTPClient httpClient = new HTTPClient();
    private Scanner scanner = new Scanner(System.in);

    // --------------------------------------------- (Main Menu) ---------------------------------------------
    private Map<Integer, Command> commands = new HashMap<>();


    public CommandLineInterface() {
        // Initialize commands map
        commands.put(1, new QuestionsCommand()); // Sprint Questions
        commands.put(2, new QueryCommand()); // Query
    }

    public void start() {
        System.out.println("Welcome to the HTTP Client Application!\nTo exit, enter 99.\n");
        while (true) {
            displayMainMenu();
            int choice = readIntInput();
            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else if (choice == 99) {
                System.out.println("\nGoodbye!");
                System.exit(0); // Exit program
            } else {
                System.out.println("Invalid choice: " + choice);
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n------------ MAIN MENU ------------");
        System.out.println("1. Sprint Questions (Command 1)");
        System.out.println("2. Query Database (Command 2)");
        System.out.print("> ");
    }

    private int readIntInput() {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();

            System.out.print("Invalid input. Please enter a number: ");
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume remaining newline character
        return input;
    }

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

        // Invoker
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
                    System.out.println("\nGoodbye!");
                    System.exit(0); // Exit program
                } else {
                    System.out.println("Invalid choice: " + choice);
                }
            }
        }

        private void displayQuestionsCommandMenu() {
            System.out.println("\n------------ QUESTIONS ------------");
            System.out.println("1. What airports are in what cities? (Sub-command 1)");
            System.out.println("2. List all aircraft passengers have travelled on? (Sub-command 2)");
            System.out.println("3. Which airports can aircraft take off from and land at? (Sub-command 3)");
            System.out.println("4. What airports have passengers used? (Sub-command 4)");
            System.out.println("0. Back");
            System.out.print("> ");
        }

        // (Questions --> 1. What airports are in what cities?)
        private class Question1SubCommand implements SubCommand {
            @Override
            public void execute() {
                httpClient.query("cities_airports");
            }

        }

        // (Questions --> 2. List all aircraft passengers have travelled on?)
        private class Question2SubCommand implements SubCommand {
            @Override
            public void execute() {
                httpClient.query("aircraft_passengers");
            }
        }

        // (Questions --> 3. Which airports can aircraft take off from and land at?)
        private class Question3SubCommand implements SubCommand {
            @Override
            public void execute() {
                httpClient.query("aircraft_airports");
            }
        }

        // (Questions --> 4. What airports have passengers used?)
        private class Question4SubCommand implements SubCommand {
            @Override
            public void execute() {
                httpClient.query("airports_passengers");
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
                    System.out.println("\nGoodbye!");
                    System.exit(0); // Exit program
                } else {
                    System.out.println("Invalid choice: " + choice);
                }
            }
        }

        private void displayQueryCommandMenu() {
            System.out.println("\n-------- SELECT QUERY TYPE --------");
            System.out.println("1. Get All (Sub-command 1)");
            System.out.println("2. Get By ID (Sub-command 2)");
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
                    displaySubCommand1Menu();
                    int choice = readIntInput();
                    SubSubCommand subSubCommand = subSubCommands.get(choice);
                    if (subSubCommand != null) {
                        subSubCommand.execute();
                    } else if (choice == 0) {
                        return; // exit sub-menu
                    } else if (choice == 99) {
                        System.out.println("\nGoodbye!");
                        System.exit(0); // Exit program
                    } else {
                        System.out.println("Invalid choice: " + choice);
                    }
                }
            }

            private void displaySubCommand1Menu() { // Get All Menu
                System.out.println("\n------ GET ALL: SELECT TABLE ------");
                System.out.println("1. Cities (Sub-sub-command 1)");
                System.out.println("2. Passengers (Sub-sub-command 2)");
                System.out.println("3. Airports (Sub-sub-command 3)");
                System.out.println("4. Aircraft (Sub-sub-command 4)");
                System.out.println("0. Back");
                System.out.print("> ");
            }

            // ----------- Sub-Sub-Command Execution (Query --> Get All --> <#. Sub-Sub-Command>) ----------

            // (Query --> Get All --> 1. Cities)
            private class GetAllCitiesSubSubCommand implements QueryCommand.SubSubCommand {
                @Override
                public void execute() {
//                    System.out.println("CITIES: Executing sub-sub-command 1 of sub-command 1 of command 2");
                    httpClient.query("cities");
                }
            }

            // (Query --> Get All --> 2. Passengers)
            private class GetAllPassengersSubSubCommand implements QueryCommand.SubSubCommand {
                @Override
                public void execute() {
//                    System.out.println("PASSENGERS: Executing sub-sub-command 2 of sub-command 1 of command 2");
                    httpClient.query("passengers");
                }
            }

            // (Query --> Get All --> 3. Airports)
            private class GetAllAirportsSubSubCommand implements QueryCommand.SubSubCommand {
                @Override
                public void execute() {
//                    System.out.println("AIRPORTS: Executing sub-sub-command 3 of sub-command 1 of command 2");
                    httpClient.query("airports");
                }
            }

            // (Query --> Get All --> 4. Aircraft)
            private class GetAllAircraftSubSubCommand implements QueryCommand.SubSubCommand {
                @Override
                public void execute() {
//                    System.out.println("AIRCRAFT: Executing sub-sub-command 4 of sub-command 1 of command 2");
                    httpClient.query("aircraft");
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
                    displaySubCommand2Menu();
                    int choice = readIntInput();

                    if (choice == 0) {
                        return; // exit sub-menu
                    }

                    if (choice == 99) {
                        System.out.println("\nGoodbye!");
                        System.exit(0); // Exit program
                    }

                    SubSubCommandWithInt subSubCommand = subSubCommandsWithID.get(choice);

                    if (subSubCommand == null) {
                        System.out.println("Invalid choice: " + choice);
                    }

                    if (subSubCommand != null) {
                        String chosenEntity = null;
                        switch (choice) {
                            case 1 -> chosenEntity = "CITY";
                            case 2 -> chosenEntity = "PASSENGER";
                            case 3 -> chosenEntity = "AIRPORT";
                            case 4 -> chosenEntity = "AIRCRAFT";
                        }
                        System.out.printf("\n------- GET BY %s: ENTER ID -------\n", chosenEntity);
                        System.out.print("Please enter an integer ID: ");
                        while (true) {
                            int id = readIntInput();
                            if (id < 0) {
                                System.out.print("Invalid input. Please enter a valid whole number: ");
                            } else {
                                subSubCommand.execute(id);
                                break;
                            }
                        }
                    }
//                    if (subSubCommand != null) {
//                        String chosenEntity = null;
//                        switch (choice) {
//                            case 1 -> chosenEntity = "CITY";
//                            case 2 -> chosenEntity = "PASSENGER";
//                            case 3 -> chosenEntity = "AIRPORT";
//                            case 4 -> chosenEntity = "AIRCRAFT";
//                        }
//
//                        System.out.printf("\n------- GET BY %s: ENTER ID -------", chosenEntity);
//                        System.out.print("Please enter an integer ID: ");
//                        subSubCommand.execute(id);
//                    } else {
//                        System.out.println("Invalid choice: " + choice);
//                    }
                }
            }


            private void displaySubCommand2Menu() {
                System.out.println("\n----- GET BY ID: SELECT TABLE -----");
                System.out.println("1. City (Sub-sub-command 1)");
                System.out.println("2. Passenger (Sub-sub-command 2)");
                System.out.println("3. Airport (Sub-sub-command 3)");
                System.out.println("4. Aircraft (Sub-sub-command 4)");
                System.out.println("0. Back");
                System.out.print("> ");
            }


            // ----------- Sub-Sub-Command Execution (Query --> Get By ID --> <#. Sub-Sub-Command>) ----------

            // (Query --> Get By ID --> 1. City)
            private class GetCityByIdSubSubCommand implements QueryCommand.SubSubCommandWithInt {
                @Override
                public void execute(int id) {
//                    System.out.println("City: Executing sub-sub-command 1 of sub-command 2 of command 2");
                    httpClient.query("city/" + id);
                }
            }

            // (Query --> Get By ID --> 2. Passenger)
            private class GetPassengerByIdSubSubCommand implements QueryCommand.SubSubCommandWithInt {
                @Override
                public void execute(int id) {
//                    System.out.println("Passenger: Executing sub-sub-command 2 of sub-command 2 of command 2");
                    // TODO: Make sure passenger endpoint is added to the backend
                    httpClient.query("passenger/" + id);
                }
            }

            // (Query --> Get By ID --> 3. Airport)
            private class GetAirportByIdSubSubCommand implements QueryCommand.SubSubCommandWithInt {
                @Override
                public void execute(int id) {
//                    System.out.println("Airport: Executing sub-sub-command 3 of sub-command 2 of command 2");
                    httpClient.query("airport/" + id);
                }
            }

            // (Query --> Get By ID --> 4. Aircraft)
            private class GetAircraftByIdSubSubCommand implements QueryCommand.SubSubCommandWithInt {
                @Override
                public void execute(int id) {
//                    System.out.println("Aircraft: Executing sub-sub-command 4 of sub-command 2 of command 2");
                    httpClient.query("aircraft/" + id);
                }
            }
        }
    }


    // Client
    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.start();
    }
}


