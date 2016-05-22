package objects;

/**
 * Created by Artyom on 11.04.2016.
 */
public class Researcher {
    private String name;
    private String university;

    public Researcher(String name, String university) {
        this.university = university;
        this.name = name;
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
