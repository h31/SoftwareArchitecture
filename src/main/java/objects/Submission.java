package objects;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Artyom on 11.04.2016.
 */
@Entity
public class Submission {
    @Id @GeneratedValue
    UUID id;
    private State state;
    @OneToOne(cascade = CascadeType.ALL)
    private Paper paper;
    @OneToOne
    private ReviewerRemark reviewerRemark;
    @OneToOne
    private EditorialRemark editorialRemark;

    public Submission() {
    }

    public Date getDate() {
        return date;
    }

    private Date date;

    public Submission(Paper paper) {
        this.date = new Date();
        this.state = State.PENDING;
        this.paper = paper;
        reviewerRemark = null;
        editorialRemark = null;
    }

    public Submission(Date date, Paper paper) {
        this(paper);
        this.date = date;
    }

    public Submission(Date date, Paper paper, State state) {
        this(date, paper);
        setState(state);
    }

    public enum State {
        PENDING,
        REVIEWER_ENQUEUED,
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

    public Optional<ReviewerRemark> getReviewerRemark() {
        return Optional.ofNullable(reviewerRemark);
    }

    public void setReviewerRemark(ReviewerRemark reviewerRemark) {
        this.reviewerRemark = reviewerRemark;
    }

    public Optional<EditorialRemark> getEditorialRemark() {
        return Optional.ofNullable(editorialRemark);
    }

    public void setEditorialRemark(EditorialRemark editorialRemark) {
        this.editorialRemark = editorialRemark;
    }
}
