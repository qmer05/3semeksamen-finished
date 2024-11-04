package app.routes;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final TripRoute tripRoute;

    public Routes(EntityManagerFactory emf) {
        tripRoute = new TripRoute(emf);
    }

    public EndpointGroup getApiRoutes() {
        return () -> {
            path("/trips", tripRoute.getRoutes());
        };
    }

}
