package app.dtos;

import app.entities.Trip;
import app.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class TripDTO {

    private Integer id;
    private LocalTime starttime;
    private LocalTime endtime;
    private String startposition;
    private String name;
    private Integer price;
    private Category category;
    private GuideDTO guide;

    private List<ItemDTO> items;

    public TripDTO(Trip trip){
        this.id = trip.getId();
        this.starttime = trip.getStarttime();
        this.endtime = trip.getEndtime();
        this.startposition = trip.getStartposition();
        this.name = trip.getName();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.guide = trip.getGuide() != null ? new GuideDTO(trip.getGuide()) : null;
    }

    public TripDTO(Integer id, LocalTime starttime, LocalTime endtime, String startposition, String name, Integer price, Category category, GuideDTO guide){
        this.id = id;
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }

    public TripDTO(LocalTime starttime, LocalTime endtime, String startposition, String name, Integer price, Category category){
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripDTO tripDTO = (TripDTO) o;
        return Objects.equals(id, tripDTO.id) && Objects.equals(starttime, tripDTO.starttime) && Objects.equals(endtime, tripDTO.endtime) && Objects.equals(startposition, tripDTO.startposition) && Objects.equals(name, tripDTO.name) && Objects.equals(price, tripDTO.price) && category == tripDTO.category && Objects.equals(guide, tripDTO.guide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, starttime, endtime, startposition, name, price, category, guide);
    }
}
