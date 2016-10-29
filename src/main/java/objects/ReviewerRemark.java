package objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

/**
 * Created by artyom on 23.05.16.
 */
@Entity
public class ReviewerRemark {
    public ReviewerRemark(Reviewer reviewer, Mark mark, String text, UUID id) {
        this.reviewer = reviewer;
        this.mark = mark;
        this.text = text;
        this.id = id;
    }

    @ManyToOne
    Reviewer reviewer;
    Mark mark;
    String text;
    @Id
    @GeneratedValue
    UUID id;

    public ReviewerRemark() {
    }

    public enum Mark {
        ACCEPT,
        NEUTRAL,
        REJECT
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public Mark getMark() {
        return mark;
    }

    public String getText() {
        return text;
    }

    public UUID getId() {
        return id;
    }
}
