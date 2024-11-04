package app.dtos;

import app.entities.Guide;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@NoArgsConstructor
@Getter
public class GuideDTO {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Integer yearsOfExperience;
    private List<Integer> tripIds = new ArrayList<>(); // Only include trip IDs to avoid circular reference

    public GuideDTO(Guide guide) {
        this.id = guide.getId();
        this.firstname = guide.getFirstname();
        this.lastname = guide.getLastname();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.yearsOfExperience = guide.getYearsOfExperience();
        if (guide.getTrips() != null) {
            guide.getTrips().forEach(trip -> tripIds.add(trip.getId()));
        }
    }

    public GuideDTO(Integer id, String firstname, String lastname, String email, String phone, Integer yearsOfExperience) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public GuideDTO(String firstname, String lastname, String email, String phone, Integer yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuideDTO guideDTO = (GuideDTO) o;
        return Objects.equals(id, guideDTO.id) && Objects.equals(firstname, guideDTO.firstname) && Objects.equals(lastname, guideDTO.lastname) && Objects.equals(email, guideDTO.email) && Objects.equals(phone, guideDTO.phone) && Objects.equals(yearsOfExperience, guideDTO.yearsOfExperience) && Objects.equals(tripIds, guideDTO.tripIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email, phone, yearsOfExperience, tripIds);
    }

    @Override
    public String toString() {
        return "GuideDTO{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", tripIds=" + tripIds +
                '}';
    }
}