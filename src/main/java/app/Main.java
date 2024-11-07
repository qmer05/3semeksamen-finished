package app;

import app.config.AppConfig;
import app.config.HibernateConfig;
import app.config.Populator;
import app.controllers.TripService;
import app.daos.GuideDAO;
import app.daos.TripDAO;
import app.dtos.ItemDTO;
import app.enums.Category;
import jakarta.persistence.EntityManagerFactory;

import java.security.Provider;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tripplanner");
        AppConfig.startServer(7000, emf);

//        TripDAO tripDAO = new TripDAO(emf);
//        GuideDAO guideDAO = new GuideDAO(emf);
//        Populator populator = new Populator(tripDAO, guideDAO, emf);
//        populator.populateGuides();
//        populator.populateTrips();

    }
}