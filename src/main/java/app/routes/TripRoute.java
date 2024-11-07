package app.routes;

import app.controllers.TripController;
import app.daos.TripDAO;
import app.security.enums.Role;
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
            post("/", tripController::createEntity, Role.ADMIN);
            get("/", tripController::getAll, Role.ANYONE);
            get("/{id}", tripController::getById, Role.ADMIN, Role.USER);
            put("/{id}", tripController::update);
            delete("/{id}", tripController::delete);
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip);
            get("/category/{category}", tripController::getTripsByCategory);
            get("/guides/totalprice", tripController::getGuideTripSummary);
            get("/guides/{guideId}", tripController::getTripsByGuide);
            get("/{id}/totalweight", tripController::getTotalWeight);
        };
    }

}
