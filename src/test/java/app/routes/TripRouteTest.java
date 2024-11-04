package app.routes;

import app.config.AppConfig;
import app.config.HibernateConfig;
import app.config.Populator;
import app.daos.GuideDAO;
import app.daos.TripDAO;
import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import app.enums.Category;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResellerRoutesTest {

    private Javalin app;
    private EntityManagerFactory emf;
    private TripDAO tripDAO;
    private GuideDAO guideDAO;

    Populator populator;

    private final String BASE_URL = "http://localhost:7000/api";

    private List<TripDTO> trips;
    private TripDTO t1, t2, t3, t4, t5;
    private List<GuideDTO> guides;
    private GuideDTO g1, g2, g3, g4, g5;

    @BeforeAll
    void init() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = AppConfig.startServer(7000, emf);
        tripDAO = new TripDAO(emf);
        guideDAO = new GuideDAO(emf);
        populator = new Populator(tripDAO, guideDAO, emf);
    }

    @BeforeEach
    void setUp() {
        trips = populator.populateTrips();
        guides = populator.populateGuides();
        t1 = trips.get(0);
        t2 = trips.get(1);
        t3 = trips.get(2);
        t4 = trips.get(3);
        t5 = trips.get(4);
        g1 = guides.get(0);
        g2 = guides.get(1);
        g3 = guides.get(2);
        g4 = guides.get(3);
        g5 = guides.get(4);
    }

    @AfterEach
    void tearDown() {
        populator.cleanUpTrips();
        populator.cleanUpGuides();
    }

    @AfterAll
    void closeDown() {
        AppConfig.stopServer(app);
    }

    @Test
    void testGetTrips() {
        TripDTO[] trips = given()
                .when()
                .get(BASE_URL + "/trips")
                .then()
                .statusCode(200)
                .extract()
                .as(TripDTO[].class);

        assertThat(trips, arrayWithSize(5));
        assertThat(trips, arrayContainingInAnyOrder(t1, t2, t3, t4, t5));
    }

    @Test
    void testGetTripById() {
        TripDTO tripDTO = given()
                .when()
                .get(BASE_URL + "/trips/2")
                .then()
                .statusCode(200)
                .extract()
                .as(TripDTO.class);

        assertThat(tripDTO, equalTo(t2));
        assertThat(tripDTO.getName(), is(t2.getName()));
    }

    @Test
    void testCreateTrip() {
        TripDTO trip = new TripDTO(LocalTime.of(10, 0), LocalTime.of(12, 0), "Startposition", "Trip", 100, Category.FOREST);
        TripDTO createdTrip = given()
                .contentType("application/json")
                .body(trip)
                .when()
                .post(BASE_URL + "/trips")
                .then()
                .statusCode(201)
                .extract()
                .as(TripDTO.class);

        // will not work because of the reseller doesn't have an id, why compare other attributes
        // assertThat(createdTrip, equalTo(trip));
        assertThat(createdTrip.getName(), is(trip.getName()));
        assertThat(createdTrip.getStartposition(), is(trip.getStartposition()));
        assertThat(createdTrip.getStarttime(), is(trip.getStarttime()));
        assertThat(tripDAO.getAll(), hasSize(6));
    }

    @Test
    void testDeleteTrip() {
        given()
                .when()
                .delete(BASE_URL + "/trips/3")
                .then()
                .statusCode(204);

        assertThat(tripDAO.getAll(), hasSize(4));
        assertThat(tripDAO.getAll(), not(hasItem(t3)));
        assertThat(tripDAO.getAll(), containsInAnyOrder(t1, t2, t4, t5));
    }

    @Test
    void testUpdateTrip() {
        TripDTO trip = new TripDTO(LocalTime.of(17, 0), LocalTime.of(22, 0), "Startposition updated", "Trip updated", 100, Category.CITY);
        TripDTO updatedTrip = given()
                .contentType("application/json")
                .body(trip)
                .when()
                .put(BASE_URL + "/trips/5")
                .then()
                .statusCode(200)
                .extract()
                .as(TripDTO.class);

        // will not work because of the reseller doesn't have an id, why compare other attributes
        // assertThat(createdTrip, equalTo(trip));
        assertThat(updatedTrip.getName(), is(trip.getName()));
        assertThat(updatedTrip.getStartposition(), is(trip.getStartposition()));
        assertThat(updatedTrip.getStarttime(), is(trip.getStarttime()));
        assertThat(tripDAO.getAll(), hasSize(5));
    }

    @Test
    void testAddGuideToTrip() {
        TripDTO updatedTrip = given()
                .when()
                .put(BASE_URL + "/trips/1/guides/2")
                .then()
                .statusCode(200).extract()
                .as(TripDTO.class);

        assertThat(updatedTrip.getGuide(), notNullValue());
        assertThat(updatedTrip.getGuide().getId(), is(g2.getId()));
        assertThat(updatedTrip.getGuide().getTripIds(), contains(1));
    }
}