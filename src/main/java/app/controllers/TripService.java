package app.controllers;

import app.dtos.ItemDTO;
import app.enums.Category;
import app.exceptions.ApiException;
import com.fasterxml.jackson.databind.JsonNode;
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

    public List<ItemDTO> getPackingItemsByCategory(Category category) {
        try {
            // Build the request URI
            URI uri = new URI("https://packingapi.cphbusinessapps.dk/packinglist/" + category.toString().toLowerCase());

            // Create the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            // Send the request
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode jsonNode = rootNode.get("items");

            // Check for successful response
            if (response.statusCode() == 200) {
                // Parse JSON response to get PackingListResponse
                return objectMapper.readValue(jsonNode.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDTO.class));
            } else {
                throw new ApiException(response.statusCode(), "Failed to fetch packing items for category: " + category);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching packing items", e);
        }
    }
}