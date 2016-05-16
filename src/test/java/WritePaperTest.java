import objects.Journal;
import objects.Paper;
import objects.Researcher;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by Artyom on 11.04.2016.
 */
public class WritePaperTest {
    private Repository repo;

    @Before
    public void init() {
        Repository repo = Repository.renewRepo();

        repo.researchers.add(new Researcher("Peter", "MIT"));
        repo.researchers.add(new Researcher("Mikhail", "SPbSTU"));

        repo.journals.add(new Journal("Nature", "ALL CAPS", false));
        repo.journals.add(new Journal("Science", "14pt", true));
    }

    @Test
    public void writeAndFormatPaper() {
        Researcher r = repo.researchers.get("Peter");
        Paper writtenPaper = new Paper("Code Uglify",
                Collections.singletonList(r),
                Collections.singletonList("code"),
                "Abstract",
                "Text");
        Journal journal = repo.journals.getList().stream()
                .filter(p -> !p.isFormattedByEditors())
                .findFirst()
                .get();
        String rules = journal.getFormatRules();
        System.out.println("Rules are: " + rules);

        writtenPaper.setContent("Formatted text");
    }
}
