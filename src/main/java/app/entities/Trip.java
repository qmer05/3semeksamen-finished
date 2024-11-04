package app.entities;

import app.dtos.TripDTO;
import app.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Table(name = "trips")
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "start_time", nullable = false)
    private LocalTime starttime;

    @Setter
    @Column(name = "end_time", nullable = false)
    private LocalTime endtime;

    @Setter
    @Column(name = "start_position", nullable = false)
    private String startposition;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private Integer price;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Setter
    @ManyToOne
    private Guide guide;

    public Trip(TripDTO tripDTO) {
        this.id = tripDTO.getId();
        this.starttime = tripDTO.getStarttime();
        this.endtime = tripDTO.getEndtime();
        this.startposition = tripDTO.getStartposition();
        this.name = tripDTO.getName();
        this.price = tripDTO.getPrice();
        this.category = tripDTO.getCategory();
    }
}
