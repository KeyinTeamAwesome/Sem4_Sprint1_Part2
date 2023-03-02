package com.keyin.httpclient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
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
// TODO: Display message if response is empty (For example, if the user enters an id that doesn't exist) (error: cannot map.size() of null)
// TODO: Find out why question uriPath responses are causing an error at: Map<String, Object> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
public class HTTPClient {
    private final HttpClient client;

    public HTTPClient() {
        this.client = HttpClient.newHttpClient();
    }

    public static HttpRequest createRequest(String uriOrigin, String uriPath) throws URISyntaxException {
        // Create a URI from the uriOrigin and uriPath strings
        URI uri = new URI(uriOrigin + uriPath);

        // Build the request using the URI, request method, and headers
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(uri);

        return builder.build();
    }

    public static HttpResponse<String> sendHttpRequest(HttpClient client, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.printf("❗ (%d) Error: Request failed.", response.statusCode());
            System.out.println("\n");
        }

        return response;
    }

    public static JSONArray parseJson(String jsonString) throws ParseException {
        // Check if the JSON string is the format of an array of objects
        // If not, add square brackets to format it as if it's an array of objects (simpler to parse)
        /*
         TODO:
           With fresh eyes, check if I'm being silly and there's a better way to do this
           I can either keep it in, or alter the backend getbyid code to always return an array of object(s):
           ----------------------------------- Replacement Code (Tested) ------------------------------------
                      @GetMapping("/city/{id}")
                      public List<City> getCityById(@PathVariable Long id) {
                          Optional<City> optionalCity = repo.findById(id);
                          List<City> cities = new ArrayList<>();
                          optionalCity.ifPresent(cities::add);
                          return cities;
                      }
        */

        if (!(jsonString.startsWith("[") && jsonString.endsWith("]"))) {
            jsonString = "[" + jsonString + "]";
        }

        // Try to parse JSON string to JSON array
        JSONParser parser = new JSONParser();

        JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

        return jsonArray;
    }

    public JSONArray formatJson(List<String> jsonArray) throws JsonProcessingException {

        // Create an array to store the JSON strings after they're formatted
        JSONArray formattedJSONArray = new JSONArray();

        // Create a LinkedHashMap to store key/value pairs
        Map<String, Object> jsonMap = new LinkedHashMap<>();

        // TODO: Find out how to fix casting error for question responses
        //  -------------------------------------------------------------------------------------------------
        //          FIXED(?): I think I fixed it by changing the backend code to return an array of
        //          objects in the correct format.
        //          -----------------------------------------------------------------------------------------
        //          @GetMapping("/cities_airports")
        //          private List<City> getAllCitiesByAirports() throws JsonProcessingException {
        //              List <City> a = (List<City>) repo.findAll();
        //              List n = new ArrayList();
        //              a.forEach(i -> {
        //                  JSONObject o = new JSONObject();
        //                  o.put("id", i.getId());
        //                  o.put("cityName", i.getCityName());
        //                  o.put("cityState", i.getCityState());
        //                  o.put("airports", i.getAirports());

        // Iterate through the JSON array
        for (Object obj : jsonArray) {
            JSONObject jsonObj = (JSONObject) obj;

            if (jsonObj != null) {
                // Create a TreeMap to store the object properties
                Map<String, Object> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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
                formattedJSONArray.add(jsonAsString);

            }
        }
        return formattedJSONArray;
    }

    public static void displayJSON(JSONArray formattedJSONArray) {
        // Print the list of formatted JSON strings
        System.out.println("-------------- JSON: --------------\n");

        // formattedJSONStringlist could simply be printed as it is, but this loop will increase
        // readability by adding new line after each record, while still being valid JSON.
        int i = 0;
        if (formattedJSONArray.size() == 0) {
            System.out.println("There is no information to be displayed.");
        }
        for (Object formattedJSONString : formattedJSONArray) {
            if (formattedJSONArray == null) {
                System.out.println("There is no information to be displayed.");
            }
            // Check if the current object is the last object in the list
            // If so, print the JSON string without a comma at the end
            if (i++ == formattedJSONArray.size() - 1) {
                System.out.println(formattedJSONString + "\n");

            } else {
                System.out.println(formattedJSONString + ",\n");
            }
        }
    }

    public void runTask(String uriOrigin, String uriPath) throws IOException, InterruptedException, URISyntaxException, ParseException {
        HttpRequest request = createRequest(uriOrigin, uriPath);
        System.out.println("\n------------ ENDPOINT: ------------\n");
        System.out.println(uriOrigin + uriPath + "\n");

        String response = sendHttpRequest(client, request).body();
        System.out.println("------------ RAW DATA: ------------\n");
        System.out.println(response + "\n");

        JSONArray jsonArray = parseJson(response);


        JSONArray formattedJSONArray = formatJson(jsonArray);

        displayJSON(formattedJSONArray);
    }
}
