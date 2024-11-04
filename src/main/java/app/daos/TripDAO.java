package app.daos;

import app.dtos.TripDTO;
import app.entities.Guide;
import app.entities.Trip;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TripDAO implements IDAO<TripDTO>, ITripGuideDAO {

    private final EntityManagerFactory emf;

    public TripDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery("SELECT new app.dtos.TripDTO(t) FROM Trip t", TripDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public TripDTO getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);
            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public TripDTO update(int id, TripDTO tripDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            trip.setCategory(tripDTO.getCategory());
            trip.setPrice(tripDTO.getPrice());
            trip.setName(tripDTO.getName());
            trip.setEndtime(tripDTO.getEndtime());
            trip.setStarttime(tripDTO.getStarttime());
            trip.setStartposition(tripDTO.getStartposition());
            em.merge(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                em.remove(trip);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public TripDTO addGuideToTrip(int tripId, int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);

            if (trip != null && guide != null) {
                // Check if the relationship already exists
                if (trip.getGuide() == null) {
                    trip.setGuide(guide);
                    guide.getTrips().add(trip);
                    em.merge(trip);
                    em.merge(guide);
                } else {
                    throw new ApiException(400, "Guide already assigned to trip");
                }
            }
            em.getTransaction().commit();
            return new TripDTO(trip);
        }
    }

    @Override
    public List<TripDTO> getTripsByGuide(int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery("SELECT new app.dtos.TripDTO(t) FROM Trip t JOIN t.guide g WHERE g.id = :guideId", TripDTO.class);
            query.setParameter("guideId", guideId);
            return query.getResultList();
        }
    }
}
