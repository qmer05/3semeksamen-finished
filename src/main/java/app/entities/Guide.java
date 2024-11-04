package app.entities;

import app.dtos.GuideDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "guides")
public class Guide {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "first_name", nullable = false)
    private String firstname;

    @Column (name = "last_name", nullable = false)
    private String lastname;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String phone;

    @Column (name = "years_of_experience", nullable = false)
    private Integer yearsOfExperience;

    @OneToMany (mappedBy = "guide", fetch = FetchType.EAGER)
    private List<Trip> trips;

    public Guide (GuideDTO guideDTO) {
        this.id = guideDTO.getId();
        this.firstname = guideDTO.getFirstname();
        this.lastname = guideDTO.getLastname();
        this.email = guideDTO.getEmail();
        this.phone = guideDTO.getPhone();
        this.yearsOfExperience = guideDTO.getYearsOfExperience();
    }

}
