package objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artyom on 11.04.2016.
 */
@Entity
public class Journal {
    @Id
    private String name;
    private String formatRules;
    private boolean formattedByEditors;

    @OneToMany
    private List<Submission> submissions = new ArrayList<>();

    public Journal(String name, String formatRules, boolean formattedByEditors) {
        this.name = name;
        this.formatRules = formatRules;
        this.formattedByEditors = formattedByEditors;
    }

    public Journal() {
    }

    public void setFormatRules(String formatRules) {
        this.formatRules = formatRules;
    }

    public void setFormattedByEditors(boolean formattedByEditors) {
        this.formattedByEditors = formattedByEditors;
    }

    public boolean isFormattedByEditors() {
        return formattedByEditors;
    }

    public String getName() {
        return name;
    }

    public String getFormatRules() {
        return formatRules;
    }

    public void addSubmission(Submission s) {
        submissions.add(s);
    }

//    public Submission getLatestSubmission(Researcher r) {
//        return submissions.stream()
//                .filter(p -> p.getPaper().getAuthors().contains(r)).sorted()
//    }
}
