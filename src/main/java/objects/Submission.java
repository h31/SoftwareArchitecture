package objects;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Artyom on 11.04.2016.
 */
public class Submission {
    private State state;
    private Paper paper;
    private Optional<ReviewerRemark> reviewerRemark;
    private Optional<EditorialRemark> editorialRemark;

    public Date getDate() {
        return date;
    }

    private Date date;

    public Submission(Date date, Paper paper) {
        this.date = date;
        this.state = State.PENDING;
        this.paper = paper;
        reviewerRemark = Optional.empty();
        editorialRemark = Optional.empty();
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
        return reviewerRemark;
    }

    public void setReviewerRemark(ReviewerRemark reviewerRemark) {
        this.reviewerRemark = Optional.ofNullable(reviewerRemark);
    }

    public Optional<EditorialRemark> getEditorialRemark() {
        return editorialRemark;
    }

    public void setEditorialRemark(EditorialRemark editorialRemark) {
        this.editorialRemark = Optional.ofNullable(editorialRemark);
    }
}
