package objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Created by Artyom on 11.04.2016.
 */
@Entity
public class EditorialRemark {
    public EditorialRemark(Decision decision, String remark) {
        this.decision = decision;
        this.remark = remark;
    }

    @Id @GeneratedValue
    UUID id;
    Decision decision;
    String remark;

    public EditorialRemark() {
    }

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
