package app.config;

import app.daos.GuideDAO;
import app.daos.TripDAO;
import app.dtos.GuideDTO;
import app.dtos.TripDTO;
import app.enums.Category;
import app.security.entities.Role;
import app.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Populator {

    private static GuideDAO guideDAO;
    private static TripDAO tripDAO;
    private static EntityManagerFactory emf;

    public Populator(TripDAO tripDAO, GuideDAO guideDAO, EntityManagerFactory emf) {
        this.tripDAO = tripDAO;
        this.guideDAO = guideDAO;
        this.emf = emf;
    }

    public static UserDTO[] populateUsers(EntityManagerFactory emf) {
        User user, admin;
        Role userRole, adminRole;

        user = new User("usertest", "user123");
        admin = new User("admintest", "admin123");
        userRole = new Role("USER");
        adminRole = new Role("ADMIN");
        user.addRole(userRole);
        admin.addRole(adminRole);

        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.getTransaction().commit();
        }
        UserDTO userDTO = new UserDTO(user.getUsername(), "user123");
        UserDTO adminDTO = new UserDTO(admin.getUsername(), "admin123");
        return new UserDTO[]{userDTO, adminDTO};
    }

    public List<GuideDTO> populateGuides() {

        GuideDTO g1 = new GuideDTO("John", "Doe", "john@doe.com", "4040994324", 10);
        GuideDTO g2 = new GuideDTO("Jane", "Smith", "jane@smith.com", "4040898765", 15);
        GuideDTO g3 = new GuideDTO("Alice", "Johnson", "alice@johnson.com", "4040795432", 12);
        GuideDTO g4 = new GuideDTO("Bob", "Brown", "bob@brown.com", "4040687654", 20);
        GuideDTO g5 = new GuideDTO("Charlie", "Davis", "charlie@davis.com", "4040583245", 18);

        GuideDTO guide1 = guideDAO.create(g1);
        GuideDTO guide2 = guideDAO.create(g2);
        GuideDTO guide3 = guideDAO.create(g3);
        GuideDTO guide4 = guideDAO.create(g4);
        GuideDTO guide5 = guideDAO.create(g5);

        List<GuideDTO> guides = new ArrayList<>();
        guides.add(guide1);
        guides.add(guide2);
        guides.add(guide3);
        guides.add(guide4);
        guides.add(guide5);

        return guides;
    }

    public List<TripDTO> populateTrips() {

        TripDTO t1 = new TripDTO(LocalTime.of(9, 0), LocalTime.of(12, 0), "Montreal", "Hiking", 1000, Category.FOREST);
        TripDTO t2 = new TripDTO(LocalTime.of(10, 0), LocalTime.of(13, 0), "Quebec", "Biking", 2000, Category.CITY);
        TripDTO t3 = new TripDTO(LocalTime.of(11, 0), LocalTime.of(14, 0), "Paris", "Skiing", 3000, Category.SNOW);
        TripDTO t4 = new TripDTO(LocalTime.of(12, 0), LocalTime.of(15, 0), "Berlin", "Swimming", 4000, Category.LAKE);
        TripDTO t5 = new TripDTO(LocalTime.of(13, 0), LocalTime.of(16, 0), "Copenhagen", "Fishing", 5000, Category.SEA);

        TripDTO trip1 = tripDAO.create(t1);
        TripDTO trip2 = tripDAO.create(t2);
        TripDTO trip3 = tripDAO.create(t3);
        TripDTO trip4 = tripDAO.create(t4);
        TripDTO trip5 = tripDAO.create(t5);

        List<TripDTO> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        trips.add(trip4);
        trips.add(trip5);

        return trips;
    }

    public void cleanUpGuides() {
        // Delete all data from database
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Guide").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE guides_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanUpTrips() {
        // Delete all data from database
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Trip").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE trips_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanUpUsers(){
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role ").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
