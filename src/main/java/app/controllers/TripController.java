package app.controllers;

import app.daos.TripDAO;
import app.dtos.ItemDTO;
import app.dtos.TripDTO;
import app.enums.Category;
import app.exceptions.ApiException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TripController {

    private final Logger log = LoggerFactory.getLogger(TripController.class);
    private final TripDAO tripDAO;
    private final TripService tripService;

    public TripController(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
        this.tripService = new TripService();
    }

    public void getById(Context ctx) {
        try {
            // request
            int id = Integer.parseInt(ctx.pathParam("id"));
            // DTO
            TripDTO tripDTO = tripDAO.getById(id);

            // Fetch packing items by category
            List<ItemDTO> items = tripService.getPackingItemsByCategory(tripDTO.getCategory());
            tripDTO.setItems(items);
            // response
            ctx.res().setStatus(200);
            ctx.json(tripDTO, TripDTO.class);
        } catch (Exception e) {
            //evt. skrive no trip found + id i stedet for e.getMessage()
            log.error("404 {}", e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getAll(Context ctx) {
        try {
            // List of DTOS
            List<TripDTO> trips = tripDAO.getAll();
            // If the list is empty, throw a 404 error
            if (trips.isEmpty()) {
                throw new ApiException(404, "No trips found");
            }
            // Set response status and return the list as JSON
            ctx.res().setStatus(200);
            ctx.json(trips, TripDTO.class);
        } catch (ApiException e) {
            log.error("404 {}", e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void createEntity(Context ctx) {
        try {
            // request
            TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
            // DTO
            TripDTO tripDTO = tripDAO.create(jsonRequest);
            // response
            ctx.res().setStatus(201);
            ctx.json(tripDTO, TripDTO.class);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            // dto
            TripDTO tripDTO = tripDAO.update(id, ctx.bodyAsClass(TripDTO.class));
            // response
            ctx.res().setStatus(200);
            ctx.json(tripDTO, TripDTO.class);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void delete(Context ctx) {
        try {
            // request
            int id = Integer.parseInt(ctx.pathParam("id"));

            tripDAO.delete(id);
            // response
            ctx.res().setStatus(204);
        } catch (Exception e) {
            //evt. skrive no trip found/failed to delete + id i stedet for e.getMessage()
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void addGuideToTrip(Context ctx) {
        try {
            // request
            int tripId = Integer.parseInt(ctx.pathParam("tripId"));
            int guideId = Integer.parseInt(ctx.pathParam("guideId"));
            // dto
            TripDTO tripDTO = tripDAO.addGuideToTrip(tripId, guideId);
            // response
            ctx.res().setStatus(200);
            ctx.json(tripDTO, TripDTO.class);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void getTripsByCategory(Context ctx) {
        try {
            // request
            Category category = Category.valueOf(ctx.pathParam("category"));
            // List of DTOS

            List<TripDTO> trips = tripDAO.getAll();

            List<TripDTO> newTrips = trips.stream()
                    .filter(trip -> trip.getCategory().equals(category))
                    .collect(Collectors.toList());

            // If the list is empty, throw a 404 error
            if (newTrips.isEmpty()) {
                throw new ApiException(404, "No trips with category: " + category + " found");
            }
            // Set response status and return the list as JSON
            ctx.res().setStatus(200);
            ctx.json(newTrips, TripDTO.class);
        } catch (ApiException e) {
            log.error("404 {}", e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getGuideTripSummary(Context ctx) {
        try {
            // Retrieve all trips
            List<TripDTO> trips = tripDAO.getAll();

            // Group trips by guide ID and calculate the total price per guide
            List<Map<String, Integer>> guideSummary = trips.stream()
                    .filter(trip -> trip.getGuide() != null) // Filter out trips with no assigned guide
                    .collect(Collectors.groupingBy(
                            trip -> trip.getGuide().getId(), // Group by guide ID
                            Collectors.summingInt(TripDTO::getPrice) // Sum prices per guide
                    ))
                    .entrySet()
                    .stream()
                    .map(entry -> Map.of("guideId", entry.getKey(), "totalPrice", entry.getValue()))
                    .collect(Collectors.toList());

            // If no data found, throw a 404 error
            if (guideSummary.isEmpty()) {
                throw new ApiException(404, "No trips found with guides");
            }

            // Set response status and return the summary as JSON
            ctx.res().setStatus(200);
            ctx.json(guideSummary);
        } catch (ApiException e) {
            log.error("404 {}", e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getTripsByGuide(Context ctx) {
        try {
            // request
            int guideId = Integer.parseInt(ctx.pathParam("guideId"));
            // List of DTOS
            List<TripDTO> trips = tripDAO.getTripsByGuide(guideId);
            // If the list is empty, throw a 404 error
            if (trips.isEmpty()) {
                throw new ApiException(404, "No trips found");
            }
            // Set response status and return the list as JSON
            ctx.res().setStatus(200);
            ctx.json(trips, TripDTO.class);
        } catch (ApiException e) {
            log.error("404 {}", e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getTotalWeight(Context ctx) {
        try {
            int tripId = Integer.parseInt(ctx.pathParam("id"));
            int totalWeight = tripDAO.getTotalItemWeight(tripId);
            ctx.res().setStatus(200);
            ctx.json("The sum of the weights of all packing items for the trip: " + totalWeight);
        } catch (Exception e) {
            log.error("500 {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }
}
