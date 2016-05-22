package objects;

/**
 * Created by artyom on 23.05.16.
 */
public class ReviewerRemark {
    public ReviewerRemark(Reviewer reviewer, Mark mark, String text) {
        this.reviewer = reviewer;
        this.mark = mark;
        this.text = text;
    }

    Reviewer reviewer;
    Mark mark;
    String text;

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
}
