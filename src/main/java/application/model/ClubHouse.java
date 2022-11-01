package application.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "clubhouse", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "gemeente"})
})


public class ClubHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "name.missing")
    @Size(min = 5, message = "name.too.short")
    private String name;

    @NotBlank(message = "email.missing")
    private String email;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @NotBlank(message = "type.missing")
    @Pattern(regexp="(O|HO|G)", message="type.only.O.HO.G")
    private String type;


    @Min(value = 1, message = "maxLeden.negative")
    @NotNull(message = "maxLeden.missing")
    private Integer maxLeden;

    private String gemeente;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMaxLeden() {
        return maxLeden;
    }

    public void setMaxLeden(Integer maxLeden) {
        this.maxLeden = maxLeden;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }
}
