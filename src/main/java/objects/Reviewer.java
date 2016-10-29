package objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Created by artyom on 22.05.16.
 */
@Entity
public class Reviewer {
    private String name;
    private String university;
    @Id
    @GeneratedValue
    private UUID id;

    public Reviewer(String name, String university) {
        this.university = university;
        this.name = name;
    }

    public Reviewer() {
    }

    public String getUniversity() {
        return university;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }
}
