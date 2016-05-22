package objects;

import java.util.Date;
import java.util.Objects;

/**
 * Created by Artyom on 11.04.2016.
 */
public class Submission {
    private State state;
    private Paper paper;
    private Date date;

    public Submission(Date date, Paper paper) {
        this.date = date;
        this.state = State.PENDING;
        this.paper = paper;
    }

    enum State {
        PENDING,
        EDITORIAL_REVIEW,
        REVIEWER_REVIEW,
        REJECTED,
        IN_POOL,
        PUBLISHED
    };

    public State getState() {
        return state;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setState(State state) {
        this.state = state;
    }
}
