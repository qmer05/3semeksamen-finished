package app.routes;

import app.controllers.TripController;
import app.daos.TripDAO;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.post;

public class TripRoute {

    private final TripController tripController;

    public TripRoute(EntityManagerFactory emf) {
        tripController = new TripController(new TripDAO(emf));
    }

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/", tripController::createEntity);
            get("/", tripController::getAll);
            get("/{id}", tripController::getById);
            put("/{id}", tripController::update);
            delete("/{id}", tripController::delete);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip);
            get("/category/{category}", tripController::getTripsByCategory);
            get("/guides/totalprice", tripController::getGuideTripSummary);
        };
    }

}
