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
        commands.put(1, new Command1Questions()); // Sprint Questions
        commands.put(2, new Command2Query()); // Query
    }

    public void start() {
        System.out.println("Welcome to the HTTP Client Application!\nTo exit, enter 99.\n");
        while (true) {
            displayMainMenu();
            int choice = readIntInput();
            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Invalid choice: " + choice);
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("Main Menu:");
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
    private class Command1Questions implements Command {
        private Map<Integer, SubCommand> subCommands = new HashMap<>();

        public Command1Questions() {
            subCommands.put(1, new SubCommand1Question1());
            subCommands.put(2, new SubCommand2Question2());
            subCommands.put(3, new SubCommand3Question3());
            subCommands.put(4, new SubCommand4Question4());
        }

        // Invoker
        @Override
        public void execute() {
            while (true) {
                displayCommand1QuestionsMenu();
                int choice = readIntInput();
                SubCommand subCommand = subCommands.get(choice);
                if (subCommand != null) {
                    subCommand.execute();
                } else if (choice == 0) { // Go back to previous menu
                    return; // exit sub-menu
                } else if (choice == 99) {
                    System.exit(0); // Exit program
                } else {
                    System.out.println("Invalid choice: " + choice);
                }
            }
        }

        private void displayCommand1QuestionsMenu() {
            System.out.println("(Command 1) Sprint Questions Menu:");
            System.out.println("1. What airports are in what cities? (Sub-command 1)");
            System.out.println("2. List all aircraft passengers have travelled on? (Sub-command 2)");
            System.out.println("3. Which airports can aircraft take off from and land at? (Sub-command 3)");
            System.out.println("4. What airports have passengers used? (Sub-command 4)");
            System.out.println("0. Back");
            System.out.print("> ");
        }

        private interface SubCommand {
            void execute();
        }

        // (Questions --> 1. What airports are in what cities?)
        private class SubCommand1Question1 implements SubCommand {
            @Override
            public void execute() {
                System.out.println("Executing sub-command 1 of command 1");
                // TODO: Implement HTTPClient // TEST - DELETE!
                httpClient.query("airport/passengers_search?lastName=Doe");
            }

        }

        // (Questions --> 2. List all aircraft passengers have travelled on?)
        private class SubCommand2Question2 implements SubCommand {
            @Override
            public void execute() {
                System.out.println("Executing sub-command 2 of command 1");
                // TODO: Implement HTTPClient
            }
        }

        // (Questions --> 3. Which airports can aircraft take off from and land at?)
        private class SubCommand3Question3 implements SubCommand {
            @Override
            public void execute() {
                System.out.println("Executing sub-command 3 of command 1");
                // TODO: Implement HTTPClient
            }
        }

        // (Questions --> 4. What airports have passengers used?)
        private class SubCommand4Question4 implements SubCommand {
            @Override
            public void execute() {
                System.out.println("Executing sub-command 4 of command 1");
                // TODO: Implement HTTPClient
            }
        }
    }

    // --------------------------------- Command 2 (Main Menu --> 2. Query) ---------------------------------
    private class Command2Query implements Command {

        private Map<Integer, SubCommand> subCommands = new HashMap<>();

        // Constructor / Invoker
        public Command2Query() {
            subCommands.put(1, new SubCommand1GetAll());
            // TODO: Sub-command 2 (Get By ID)
            subCommands.put(2, new SubCommand2GetByID());
        }

        // Invoker
        @Override
        public void execute() {
            while (true) {
                displayCommand2QueryMenu();
                int choice = readIntInput();
                SubCommand subCommand = subCommands.get(choice);
                if (subCommand != null) {
                    subCommand.execute();
                } else if (choice == 0) {
                    return;
                } else {
                    System.out.println("Invalid choice: " + choice);
                }
            }
        }

        private void displayCommand2QueryMenu() {
            System.out.println("Select the query type:");
            System.out.println("1. Get All (Sub-command 1)");
            System.out.println("2. Get By ID (Sub-command 2)");
            System.out.println("0. Back");
            System.out.print("> ");
        }

        private interface SubCommand {
            void execute();
        }

        private interface SubSubCommand {
            void execute();
        }

        // ------------------------------ Sub-Command 1 (Query --> 1. Get All) ------------------------------
        private class SubCommand1GetAll implements SubCommand {

            private Map<Integer, SubSubCommand> subSubCommands = new HashMap<>();

            public SubCommand1GetAll() {
                subSubCommands.put(1, new SubSubCommand1GetAllCities());
                subSubCommands.put(2, new SubSubCommandWithIDGetAllPassengers());
                subSubCommands.put(3, new SubSubCommand3GetAllAirports());
                subSubCommands.put(4, new SubSubCommand4GetAllAircraft());

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
                    } else {
                        System.out.println("Invalid choice: " + choice);
                    }
                }
            }

            private void displaySubCommand1Menu() { // Get All Menu
                System.out.println("Select Table to Get All:");
                System.out.println("1. Cities (Sub-sub-command 1)");
                System.out.println("2. Passengers (Sub-sub-command 2)");
                System.out.println("3. Airports (Sub-sub-command 3)");
                System.out.println("4. Aircraft (Sub-sub-command 4)");
                System.out.println("0. Back");
                System.out.print("> ");
            }

            // ----------- Sub-Sub-Command Execution (Query --> Get All --> <#. Sub-Sub-Command>) ----------

            // (Query --> Get All --> 1. Cities)
            private class SubSubCommand1GetAllCities implements Command2Query.SubSubCommand {
                @Override
                public void execute() {
                    System.out.println("CITIES: Executing sub-sub-command 1 of sub-command 1 of command 2");
                    httpClient.query("cities");
                }
            }

            // (Query --> Get All --> 2. Passengers)
            private class SubSubCommandWithIDGetAllPassengers implements Command2Query.SubSubCommand {
                @Override
                public void execute() {
                    System.out.println("PASSENGERS: Executing sub-sub-command 2 of sub-command 1 of command 2");
                    httpClient.query("passengers");
                }
            }

            // (Query --> Get All --> 3. Airports)
            private class SubSubCommand3GetAllAirports implements Command2Query.SubSubCommand {
                @Override
                public void execute() {
                    System.out.println("AIRPORTS: Executing sub-sub-command 3 of sub-command 1 of command 2");
                    httpClient.query("airports");
                }
            }

            // (Query --> Get All --> 4. Aircraft)
            private class SubSubCommand4GetAllAircraft implements Command2Query.SubSubCommand {
                @Override
                public void execute() {
                    System.out.println("AIRCRAFT: Executing sub-sub-command 4 of sub-command 1 of command 2");
                    httpClient.query("aircraft");
                }
            }
        }


        // TODO: -------------------------- Sub-Command 2 (Query --> 1. Get By ID) --------------------------

        private interface SubSubCommandWithID {
            void execute(int id);
        }

        private class SubCommand2GetByID implements SubCommand {

            private Map<Integer, SubSubCommandWithID> subSubCommandsWithID = new HashMap<>();

            public SubCommand2GetByID() {
                subSubCommandsWithID.put(1, new SubSubCommand1GetCityByID());
                subSubCommandsWithID.put(2, new SubSubCommandWithIDGetPassengerByID());
                subSubCommandsWithID.put(3, new SubSubCommand3GetAirportByID());
                subSubCommandsWithID.put(4, new SubSubCommand4GetAircraftByID());
            }

            @Override
            public void execute() {
                while (true) {
                    displaySubCommand2Menu();
                    int choice = readIntInput();

                    if (choice == 0) {
                        return; // exit sub-menu
                    }

                    System.out.print("Please enter an integer ID: ");
                    int id = readIntInput();

                    SubSubCommandWithID subSubCommand = subSubCommandsWithID.get(choice);
                    if (subSubCommand != null) {
                        subSubCommand.execute(id);
                    } else {
                        System.out.println("Invalid choice: " + choice);
                    }
                }
            }

            private void displaySubCommand2Menu() {
                System.out.println("Select Table to Get By ID:");
                System.out.println("1. City (Sub-sub-command 1)");
                System.out.println("2. Passenger (Sub-sub-command 2)");
                System.out.println("3. Airport (Sub-sub-command 3)");
                System.out.println("4. Aircraft (Sub-sub-command 4)");
                System.out.println("0. Back");
                System.out.print("> ");
            }


            // ----------- Sub-Sub-Command Execution (Query --> Get By ID --> <#. Sub-Sub-Command>) ----------

            // (Query --> Get By ID --> 1. City)
            private class SubSubCommand1GetCityByID implements Command2Query.SubSubCommandWithID {
                @Override
                public void execute(int id) {
                    System.out.println("City: Executing sub-sub-command 1 of sub-command 2 of command 2");
                    httpClient.query("city/" + id);
                }
            }

            // (Query --> Get By ID --> 2. Passenger)
            private class SubSubCommandWithIDGetPassengerByID implements Command2Query.SubSubCommandWithID {
                @Override
                public void execute(int id) {
                    System.out.println("Passenger: Executing sub-sub-command 2 of sub-command 2 of command 2");
                    httpClient.query("passenger/" + id);
                }
            }

            // (Query --> Get By ID --> 3. Airport)
            private class SubSubCommand3GetAirportByID implements Command2Query.SubSubCommandWithID {
                @Override
                public void execute(int id) {
                    System.out.println("Airport: Executing sub-sub-command 3 of sub-command 2 of command 2");
                    httpClient.query("airport/" + id);
                }
            }

            // (Query --> Get By ID --> 4. Aircraft)
            private class SubSubCommand4GetAircraftByID implements Command2Query.SubSubCommandWithID {
                @Override
                public void execute(int id) {
                    System.out.println("Aircraft: Executing sub-sub-command 4 of sub-command 2 of command 2");
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


