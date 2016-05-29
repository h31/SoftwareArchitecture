package objects;

import java.util.UUID;

/**
 * Created by Artyom on 11.04.2016.
 */
public class EditorialRemark {
    public EditorialRemark(Decision decision, String remark, UUID id) {
        this.decision = decision;
        this.remark = remark;
        this.id = id;
    }

    UUID id;
    Decision decision;
    String remark;

    public enum Decision {
        ACCEPT,
        REDIRECT,
        NEEDS_REWORK,
    }

    public Decision getDecision() {
        return decision;
    }

    public String getRemark() {
        return remark;
    }

    public UUID getId() {
        return id;
    }
}
