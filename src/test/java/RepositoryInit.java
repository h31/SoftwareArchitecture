import objects.*;
import repository.Repository;
import services.SubmissionUpdate;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by artyom on 22.05.16.
 */
public class RepositoryInit {
    private Repository repo;

    RepositoryInit() {
        repo = Repository.recreate();
    }

    void addResearchers() {
        repo.researchers.add(new Researcher("Peter", "MIT"));
        repo.researchers.add(new Researcher("Mikhail", "SPbSTU"));
    }

    void addReviewers() {
        repo.reviewers.add(new Reviewer("Rodrigo", "CMU"));
    }

    void addJournals() {
        repo.journals.add(new Journal("Nature", "ALL CAPS", false));
        repo.journals.add(new Journal("Science", "14pt", true));
    }

    void addSubmission() {
        Optional<Researcher> r = repo.researchers.get("Peter");
        Paper writtenPaper = new Paper("Code Uglify",
                Collections.singletonList(r.get()),
                Collections.singletonList("code"),
                "Abstract",
                "Text");
        repo.papers.add(writtenPaper);

        Date currentDate = new Date();

        Submission submission = new Submission(currentDate, writtenPaper);
        repo.submissions.add(submission);
    }

    void setEnqueuedSubmissions() {
        List<Submission> submissions = repo.submissions.getList();
        SubmissionUpdate update = new SubmissionUpdate(getRepo());
        for (Submission s: submissions) {
            update.editorialUpdate(s, new EditorialRemark(EditorialRemark.Decision.ACCEPT, ""));
        }
    }

    Repository getRepo() {
        return repo;
    }
}
