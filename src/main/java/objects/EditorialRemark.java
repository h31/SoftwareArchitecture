package objects;

/**
 * Created by Artyom on 11.04.2016.
 */
public class EditorialRemark {
    public EditorialRemark(Decision decision, String remark) {
        this.decision = decision;
        this.remark = remark;
    }

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
}
