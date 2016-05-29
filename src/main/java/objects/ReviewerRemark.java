package objects;

import java.util.UUID;

/**
 * Created by artyom on 23.05.16.
 */
public class ReviewerRemark {
    public ReviewerRemark(Reviewer reviewer, Mark mark, String text, UUID id) {
        this.reviewer = reviewer;
        this.mark = mark;
        this.text = text;
        this.id = id;
    }

    Reviewer reviewer;
    Mark mark;
    String text;
    UUID id;

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
