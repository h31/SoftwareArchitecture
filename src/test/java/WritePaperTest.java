import objects.Journal;
import objects.Paper;
import objects.Researcher;
import objects.Submission;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * Created by Artyom on 11.04.2016.
 */
public class WritePaperTest {
    private Repository repo;

    @Before
    public void init() {
        repo = Repository.recreate();

        repo.researchers.add(new Researcher("Peter", "MIT"));
        repo.researchers.add(new Researcher("Mikhail", "SPbSTU"));

        repo.journals.add(new Journal("Nature", "ALL CAPS", false));
        repo.journals.add(new Journal("Science", "14pt", true));
    }

    @Test
    public void writeAndFormatPaper() {
        Optional<Researcher> r = repo.researchers.get("Peter");
        assertTrue(r.isPresent());
        Paper writtenPaper = new Paper("Code Uglify",
                Collections.singletonList(r.get()),
                Collections.singletonList("code"),
                "Abstract",
                "Text");
        Optional<Journal> journal = repo.journals.getFormattedByEditors();
        assertTrue(journal.isPresent());
        String rules = journal.get().getFormatRules();
        System.out.println("Rules are: " + rules);

        writtenPaper.setContent("Formatted text");

        Date currentDate = new Date();
        Submission submission = new Submission(currentDate, writtenPaper);
        journal.get().addSubmission(submission);
    }

    @Test
    public void receivePaper() {
    }
}
