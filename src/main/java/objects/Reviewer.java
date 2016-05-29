package objects;

import java.util.UUID;

/**
 * Created by artyom on 22.05.16.
 */
public class Reviewer {
    private String name;
    private String university;
    private UUID id;

    public Reviewer(String name, String university, UUID id) {
        this.university = university;
        this.name = name;
        this.id = id;
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
