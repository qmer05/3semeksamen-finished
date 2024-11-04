package app.daos;

import app.dtos.TripDTO;

import java.util.List;

public interface ITripGuideDAO {
    TripDTO addGuideToTrip(int tripId, int guideId);
    List<TripDTO> getTripsByGuide(int guideId);
}
