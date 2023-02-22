package com.keyin.httpclient;

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

public class MainHTTPClientApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String table = null;

        // Command Line Menu Loop
        while (table == null) {
            System.out.println("Select a table:");
            System.out.println("1. Cities");
            System.out.println("2. Passengers");
            System.out.println("3. Airports");
            System.out.println("4. Aircraft");

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> table = "cities";
                    case 2 -> table = "passengers";
                    case 3 -> table = "airports";
                    case 4 -> table = "aircraft";
                    default -> System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
            System.out.println();
        }
        scanner.close();

        System.out.println("You chose: " + table + "\n");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/" + table)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("***** Raw Data: " + response.body() + "\n");
                String jsonString = response.body();

                // Parse JSON to display table data in a readable format
                JSONParser parser = new JSONParser();
                try {
                    JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

                    // Loop through array
                    for (Object obj : jsonArray) {
                        JSONObject jsonObj = (JSONObject) obj;

                        // Loop through keys, print key and value
                        for (Object keyObj : jsonObj.keySet()) {
                            String key = (String) keyObj;
                            Object value = jsonObj.get(key);
                            System.out.println(key + ": " + value);
                        }
                        System.out.println();
                    }

                    // Catch any parse exceptions
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

