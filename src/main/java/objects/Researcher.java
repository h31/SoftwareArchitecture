package objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Created by Artyom on 11.04.2016.
 */
@Entity
public class Researcher {
    @Id @GeneratedValue
    UUID id;
    private String name;
    private String university;

    public Researcher(String name, String university) {
        this.university = university;
        this.name = name;
    }

    public Researcher() {
    }

    public String getName() {
        return name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
