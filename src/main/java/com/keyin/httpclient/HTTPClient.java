package com.keyin.httpclient;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// pretty json
import java.util.*;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// TODO: Detect array of objects and print each object on a new line
public class HTTPClient {
    public void query(String endpoint) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/" + endpoint)).build();
        System.out.println("http://localhost:8080/" + endpoint);

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonString = response.body();
                System.out.println("***** Raw Data: " + jsonString + "\n");

                // Check if the JSON string is the format of an array of objects
                // If not, add square brackets to format it as if it's an array of objects (simpler to parse)
                // TODO: With fresh eyes, check if I'm being silly and there's a better way to do this
                if (!jsonString.startsWith("[") && !jsonString.endsWith("]")) {
                    jsonString = "[" + jsonString + "]";
                }

                // Try to parse JSON string to JSON array
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

                // Create a LinkedHashMap to store key/value pairs
                Map<String, Object> jsonMap = new LinkedHashMap<>();

                // Creating a list to store the formatted JSON strings for each object in the JSON array
                List<String> formattedJSONStringlist = new ArrayList<String>();

                // Loop through each object in JSON array
                for (Object obj : jsonArray) {
                    JSONObject jsonObj = (JSONObject) obj;

                    // Create a TreeMap to store the object properties
                    Map<String, Object> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); // To sort the keys in alphabetical order, use 'new TreeMap<>(String.CASE_INSENSITIVE_ORDER)'
                    treeMap.putAll(jsonObj);

                    // Making sure the "id" key is at the beginning of the LinkedHashMap... (For readability)
                    // Check if "id" key exists in the object properties
                    if (treeMap.containsKey("id")) {
                        // Remove the "id" key-value pair from the TreeMap
                        Object idValue = treeMap.remove("id");

                        // Put the "id" key-value pair at the beginning of the LinkedHashMap
                        jsonMap.put("id", idValue);
                    }

                    // Add the remaining key-value pairs to the LinkedHashMap
                    jsonMap.putAll(treeMap);

                    // Create an ObjectMapper to convert the LinkedHashMap to JSON
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                    objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

                    // Create a DefaultPrettyPrinter to format the JSON & add settings for indentation
                    DefaultPrettyPrinter printer = new DefaultPrettyPrinter().withObjectIndenter(new DefaultIndenter("\t", "\n"));
                    String jsonAsString = objectMapper.writer(printer).writeValueAsString(jsonMap);

                    // Add the formatted JSON string to the list
                    formattedJSONStringlist.add(jsonAsString);

                }

                // Print the list of formatted JSON strings
                System.out.println("------------ JSON: ------------\n");

                // formattedJSONStringlist could simply be printed as it is, but this loop will increase
                // readability by adding a new line after each JSON string, while still being valid JSON.
                int i = 0;
                for (String formattedJSONString : formattedJSONStringlist) {
                    // Check if the current object is the last object in the list
                    // If so, print the JSON string without a comma at the end
                    if (i++ == formattedJSONStringlist.size() - 1) {
                        System.out.println(formattedJSONString + "\n");

                    } else {
                        System.out.println(formattedJSONString + ",\n");
                    }
                }
            }

            // Catch any exceptions
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }
    }

//    public String stringToArrayFormatIfNeccessary(String jsonString) {
//        if (!jsonString.startsWith("[") && !jsonString.endsWith("]")) {
//            jsonString = "[" + jsonString + "]";
//        }
//        return jsonString;
//    }
}

