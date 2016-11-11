package repository;

import objects.*;
import repository.Repository;
import services.SubmissionUpdate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.*;

/**
 * Created by artyom on 22.05.16.
 */
public class RepositoryInit {
    private RepositoryLocal repo;

    public RepositoryInit(RepositoryLocal repositoryLocal) {
        this.repo = repositoryLocal;
    }

    public void addResearchers() {
        repo.getResearchers().add(new Researcher("Peter", "MIT"));
        repo.getResearchers().add(new Researcher("Mikhail", "SPbSTU"));
    }

    public void addReviewers() {
        repo.getReviewers().add(new Reviewer("Rodrigo", "CMU"));
    }

    public void addJournals() {
        repo.getJournals().add(new Journal("Nature", "ALL CAPS", false));
        repo.getJournals().add(new Journal("Science", "14pt", true));
    }

    public void addSubmission() {
        Optional<Researcher> r = repo.getResearchers().get("Peter");
        Paper writtenPaper = new Paper("Code Uglify",
                Collections.singletonList(r.get()),
                Collections.singletonList("code"),
                "Abstract",
                "Text");
        writtenPaper = repo.getPapers().add(writtenPaper);

        Date currentDate = new Date();

        Submission submission = new Submission(currentDate, writtenPaper);
        repo.getSubmissions().add(submission);
    }

    public void setEnqueuedSubmissions() {
        List<Submission> submissions = repo.getSubmissions().getList();
        SubmissionUpdate update = new SubmissionUpdate(getRepo());
        for (Submission s: submissions) {
            update.editorialUpdate(s, new EditorialRemark(EditorialRemark.Decision.ACCEPT, ""));
        }
    }

    public RepositoryLocal getRepo() {
        return repo;
    }
}
