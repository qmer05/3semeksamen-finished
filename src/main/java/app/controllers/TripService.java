package app.controllers;

import app.exceptions.ApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TripService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public TripService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void getPackingItemsByCategory(String category) {
        try {

            // Validate category
            if (!List.of("beach", "city", "forest", "lake", "sea", "snow").contains(category.toLowerCase())) {
                throw new ApiException(400, "Invalid category: " + category);
            }

            // Build the request URI
            URI uri = new URI("https://packingapi.cphbusinessapps.dk/packinglist/" + category);

            // Create the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            // Send the request
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check for successful response
            if (response.statusCode() == 200) {
                // Parse the JSON response into PackingListResponse object
                String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                        objectMapper.readTree(response.body())
                );
                System.out.println(prettyJson);
            } else {
                throw new ApiException(response.statusCode(), "Failed to fetch packing items for category: " + category);
            }
        } catch (ApiException e) {
            throw new ApiException(e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching packing items", e);
        }
    }
}

